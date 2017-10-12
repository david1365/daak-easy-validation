package da.sql;

import classes.ExternalJobTypeEntity;
import da.generality.EasyObject;
import da.generality.StringUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import java.util.*;

/**
 * Created by davood.akbari on 5/13/17.
 */
public class DASqlQuery {
    private static final String AS = " AS ";
    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";
    private static final String RIGHT_PARENTHESIS = ")";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String POINT = ".";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String NOT = " NOT ";
    private static final String IS_NOT_NULL = " IS NOT NULL ";
    private static final String IS_NULL = " IS NULL ";
    private static final String BETWEEN = " BETWEEN ";
    private static final String DISTINCT = " DISTINCT ";
    private static final String SPACE = " ";
    private static final String COMMA = ",";
    private static final String NEWLINE = "\n";
    private static final String WHERE = " WHERE ";
    private static final String EQUAL = " = ";
    private static final String GREATER_THAN = " > ";
    private static final String LESS_THAN = " < ";
    private static final String GREATER_THAN_EQUAL = " >= ";
    private static final String LESS_THAN_EQUAL = " <= ";
    private static final String LIKE = " LIKE ";
    private static final String SINGLEQ = "'";
    private static final String IN = " IN ";
    private static final String NOT_IN = NOT + IN;
    private static final String JOIN = " JOIN ";
    private static final String INNER_JOIN = " INNER JOIN ";
    private static final String LEFT_JOIN = " LEFT OUTER JOIN ";
    private static final String RIGHT_JOIN = " RIGHT OUTER JOIN ";
    private static final String ON = " ON ";
    private static final String GROUP_BY = " GROUP BY ";
    private static final String HAVING = " HAVING ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String ASC = " ASC ";
    private static final String DESC = " DESC ";
    private static final String EXISTS = " EXISTS ";
    private static final String NOT_EXISTS = NOT + EXISTS;

    private static final String START_WITH = " START_WITH ";
    private static final String CONNECT_BY_PRIOR = " CONNECT BY PRIOR ";
    private static final String CONNECT_BY_ISLEAF = " CONNECT_BY_ISLEAF ";

    private static final String UNION = " UNION ";
    private static final String UNION_ALL = " UNION ALL ";
    private static final String INTERSECT = " INTERSECT ";
    private static final String MINUS = " MINUS ";

    private static final String SUM = " SUM ";
    private static final String AVG = " AVG ";
    private static final String COUNT = " COUNT ";

    private static Session session;
    private static SessionFactory sessionFactory;

    private Boolean usedDistinct = false;

    private List<InputObject> columns = new ArrayList<>();
    private List<InputObject> tables = new ArrayList<>();

    private Map<String, String> objectQuery = new HashMap<>();
    private String objectPosition = "";

    private List<String> collectionMethods = new ArrayList<>();
    private Map<String, String> realFieldNames = new HashMap<>();

    private DASqlQuery() {
    }

    public static void setSession(Session session){
        DASqlQuery.session = session;
        DASqlQuery.sessionFactory = session.getSessionFactory();
    }

    public AbstractEntityPersister getEntityPersister(Class entityClass) {
        ClassMetadata hibernateMetadata = sessionFactory.getClassMetadata(ExternalJobTypeEntity.class);
        return (AbstractEntityPersister)hibernateMetadata;
    }
//-----------------
    private enum ObjectType{
        COLUMN,
        QUERY
    }
//-----------------
    public static class InputObject {
        private Object object;
        private String alias;
        private String tableAlias;
        private String orderbyType = "";
        private String functionName = "";
        private String realColumnName = "";
        private ObjectType objectType;

        public InputObject as(String alias) {
            this.alias = alias;

            return this;
        }

        //TODO: For what purpose
        public InputObject $(Object object) {
            this.object = object;

            return this;
        }

        public InputObject asc(){
            this.orderbyType = ASC;

            return this;
        }

        public InputObject desc(){
            this.orderbyType = DESC;

            return this;
        }

        private String getOrderbyType() {
            return orderbyType;
        }

        private String getRealColumnName() {
            return realColumnName;
        }

        private void setRealColumnName(String realColumnName) {
            this.realColumnName = realColumnName;
        }

        private ObjectType getObjectType() {
            return objectType;
        }

        private void setObjectType(ObjectType objectType) {
            this.objectType = objectType;
        }
    }

    public static class Distinct {
        private Object[] columns;
    }

    public static Distinct distinct(Object... columns) {
        Distinct distinct = new Distinct();
        distinct.columns = columns;

        return distinct;
    }

    public static InputObject $(Object object) {
        InputObject inputObject = new InputObject();

        if (object instanceof String) {
            inputObject.tableAlias = object.toString();
            return inputObject;
        }


        inputObject.object = object;

        return inputObject;
    }

    private static InputObject aggregateFunctions(String function, Object object){
        InputObject inputObject = new InputObject();//$(object);

        inputObject.object = object;

        inputObject.functionName = function;

        return inputObject;
    }


//    public static InputObject $(String tableAlias, Object column) {
//        InputObject inputObject = new InputObject();
//        inputObject.tableAlias = tableAlias;
//        inputObject.object = column;
//
//        return inputObject;
//    }

    public static InputObject _(String tableAlias){
        InputObject inputObject = new InputObject();
        inputObject.tableAlias = tableAlias;

        return inputObject;
    }

    private String realColumn(Object column) {
        InputObject inputObject = (InputObject) getRealObject(column);

        String orderByType = (objectPosition == ORDER_BY) ? inputObject.getOrderbyType() : "";

        inputObject.setRealColumnName(obtainRealColumnName(inputObject.object));

        return realTableName(inputObject) + inputObject.getRealColumnName() + orderByType;
    }

    //---------------------------------------------------------
    private String getTableNameFromObject(Object table) {
        return getTableNameFromInputObject((InputObject) getRealObject(table));
    }

    private String getTableNameFromInputObject(InputObject inputObject) {
        return (!(inputObject.object instanceof String) ? obtainTableName((Class) inputObject.object) : inputObject.object.toString())
                + SPACE + StringUtil.nvl(inputObject.alias);
    }

    private String getTableNameFromColumn(Object object) {
        Class table = EasyObject.getEntity(object);

//        Long tableLikeCount = tables.stream().filter(o-> (((Class)o.object).hashCode() == table.hashCode())).count();
        //TODO: use validateion
//        if(tableLikeCount >= 2){
//            Long tableNoAliasCount = tables.stream().filter(o-> (((Class)o.object).hashCode() == table.hashCode()) && (o.alias == null)).count();
//            if(tableNoAliasCount >= 1){
//                try {
//                    throw new Exception("two table is in from and any no alias");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Long columnNoTableAliasCount = columns.stream().filter(o-> {
//                if(!(o.object instanceof String)) {
//                    return (((Class) getEntity(o.object)).hashCode() == table.hashCode()) && (o.tableAlias == null);
//                }
//                return false;
//            }).count();
//
//            if(columnNoTableAliasCount >= 1){
//                try {
//                    throw new Exception("two column is no table alias");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        InputObject inputObject = null;
        try {
            inputObject = tables.stream().filter(o-> (((Class)o.object).hashCode() == table.hashCode())).findAny().get();
        } catch (Exception e) {
            //TODO: add correnct fanap exception
            try {
                throw new Exception("Dont find table object '" +  table.toString() + "' in 'from' part!");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


        //TODO: use validateion8
        return inputObject.alias != null ? inputObject.alias : obtainTableName(table);
    }

    private String realTableName(InputObject inputObject){
        String tableName = "";
        if (!(inputObject.object instanceof String)){
            String tableAlias = inputObject.tableAlias;
            tableName = (tableAlias == null ? getTableNameFromColumn(inputObject.object) : tableAlias) + POINT;
        }

        return tableName;
    }
//----------------------------------------------------------------

    public String obtainTableName(Class<?> entityClass) {
       return getEntityPersister(entityClass).getTableName();
    }

    public String obtainColumnName(Object column) {
        Class<?> entityClass = EasyObject.getEntity(column);

       return getEntityPersister(entityClass).getPropertyColumnNames(column.toString())[0];
    }

    public String obtainRealColumnName(Object column) {
        if (column instanceof String){
            return column.toString();
        }

        return obtainColumnName(column);
    }
//------------------------------------------------------------
    private Object checkIsQuery(InputObject inputObject) {
        Object object = inputObject.object;
        if (object instanceof DASqlQuery) {
            if (inputObject.getObjectType() == null) {
                inputObject.setObjectType(ObjectType.QUERY);
            }

            return NEWLINE + LEFT_PARENTHESIS + ((DASqlQuery) object).getQuery() + RIGHT_PARENTHESIS;
        }

        if (inputObject.getObjectType() == null) {
            inputObject.setObjectType(ObjectType.COLUMN);
        }
        return object;
    }

    private Object getRealObject(Object object) {
        InputObject inputObject = new InputObject();

        if (object instanceof InputObject) {
            inputObject = (InputObject) object;
            inputObject.object = checkIsQuery(inputObject);

            return inputObject;
        }

        inputObject.object = object;
        inputObject.object = checkIsQuery(inputObject);

        return inputObject;
    }

    private <T> void add(List<T> list, Object... objects) {
        for (Object object : objects) {
            list.add((T) getRealObject(object));
        }
    }

    private void put(Map<Class, InputObject> map, Object... objects) {
        for (Object object : objects) {
            InputObject inputObject = (InputObject) getRealObject(object);

            map.put((Class) inputObject.object, inputObject);
        }
    }

    private void saveTables(Object...tables) {
        this.add(this.tables, tables);
    }

    private Object realValue(Object object){
        //TODO:isText and isNumber require for this (use validation)
        Boolean isString = object instanceof String;
        Boolean isNumber = object instanceof Number;
        Boolean isInputObject = object instanceof InputObject;

        return  (isNumber || isString)
                    ? (isString ? SINGLEQ + object.toString() + SINGLEQ : object.toString())
                    : realColumn(object);
    }

    private String realValues(Object...objects){
        return Arrays.stream(objects)
                .map((inputObject)-> realValue(inputObject))
                .reduce((a, b)-> a + COMMA + b).get().toString();
    }

    private String addParenthesis(String value){
        return LEFT_PARENTHESIS + value + RIGHT_PARENTHESIS;
    }

    private void putInStringPosition(String value){
        objectQuery.put(objectPosition, StringUtil.nvl(objectQuery.get(objectPosition)) + value);
    }

    //----------------------------------------------------------------
    //TODO: use from yours validation
    private static void validateDistinct(Object... columns) {
        Boolean findDistinct = false;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] instanceof Distinct) {
                findDistinct = true;
            }

            if ((i > 0) && (findDistinct)) {
                try {
                    throw new Exception("The column and distinct together unauthorized! ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
 //-------------------------------------------------------------
    public static InputObject sum(Object object){
     return aggregateFunctions(SUM, object);
 }

    public static InputObject count(Object object){
        return aggregateFunctions(COUNT, object);
    }

    public static InputObject avg(Object object){
        return aggregateFunctions(AVG, object);
    }
//----------------------------------------------------------------
    public static DASqlQuery select(Object... columns) {
        DASqlQuery me = new DASqlQuery();

        //TODO: use from yours validation
        validateDistinct(columns);

        if (columns[0] instanceof Distinct) {
            me.usedDistinct = true;
            columns = ((Distinct) columns[0]).columns;
        }

        me.<InputObject>add(me.columns, columns);

        return me;
    }

//---------------------------------------------------------------
    //TODO: complete From
    public static DASqlQuery From(Object... tables) {

//        for(Object object: tables){
//            Table entityTable = new Table((Class) object);
//            (tables)
//        }

        return select()
                .from(tables);
    }
//-----
    public DASqlQuery from(Object... tables) {
        //TODO: throw no real table or feild
        //TODO remove this comment
        //this.put(this.tables, tables);
        saveTables(tables);

        String from = FROM + this.tables.stream()
                .map((inputObject)-> getTableNameFromInputObject(inputObject))
                .reduce((a, b)-> a + COMMA + b).get();

        objectQuery.put(FROM, from);


        return this;
    }

//-------------------------------------------------------------
    private DASqlQuery getWhereObject(String object){
        putInStringPosition(NEWLINE + object);

        return this;
    }

    private DASqlQuery conditionClause(String objectPosition, Object column){
        this.objectPosition = objectPosition;
        putInStringPosition(objectPosition + realColumn(column));

        return this;
    }

    public DASqlQuery where(Object column) {
        //TODO: throw exception if not from and throw for all part of query
        return conditionClause(WHERE, column);
    }

    public DASqlQuery and(Object column) {
        and();
        where(column);

        return this;
    }

    public DASqlQuery and() {
        return getWhereObject(AND);
    }

    public DASqlQuery or(Object column) {
        or();
        where(column);

        return this;
    }

    public DASqlQuery or() {
        return getWhereObject(OR);
    }


    public DASqlQuery not(Object column) {
        putInStringPosition(NOT);
        where(column);


        return this;
    }

    public DASqlQuery not() {
        return getWhereObject(NOT);
    }

    public DASqlQuery isNotNull() {
        return getWhereObject(IS_NOT_NULL);
    }

    public DASqlQuery isNull() {
        return getWhereObject(IS_NULL);
    }

    public DASqlQuery between(Object value) {
        //TODO: add get real value for string-integer-array ....
        putInStringPosition(BETWEEN + addParenthesis(realValue(value).toString()));

        return this;
    }

    private DASqlQuery getArethmatic(String mark, Object value){
        putInStringPosition(mark + realValue(value));

        return this;
    }

    private DASqlQuery getArethmatic(String mark, String value){
        putInStringPosition(mark + value);

        return this;
    }

    public DASqlQuery equal(Object value) {
        return getArethmatic(EQUAL, value);
    }

    public DASqlQuery greaterThan(Object value) {
        return getArethmatic(GREATER_THAN, value);
    }

    public DASqlQuery lessThan(Object value) {
        return getArethmatic(LESS_THAN, value);
    }

    public DASqlQuery greaterThanEqual(Object value) {
        return getArethmatic(GREATER_THAN_EQUAL, value);
    }

    public DASqlQuery lessThanEqual(Object value) {
        return getArethmatic(LESS_THAN_EQUAL, value);
    }

    public DASqlQuery like(Object value) {
        return getArethmatic(LIKE, value);
    }

    public DASqlQuery in(Object...values) {
        return getArethmatic(IN, addParenthesis(realValues(values)));
    }

    public DASqlQuery notIn(Object...values) {
        return getArethmatic(NOT_IN, addParenthesis(realValues(values)));
    }

    public DASqlQuery exists(DASqlQuery value) {
        return getArethmatic(EXISTS, realValue(value).toString());
    }

    public DASqlQuery notExists(DASqlQuery value) {
        return getArethmatic(NOT_EXISTS, realValue(value).toString());
    }

    //--------------------------------------------------------------
    private DASqlQuery connectByPrior(String connectByObject, Object column) {
        objectPosition = CONNECT_BY_PRIOR;

        putInStringPosition(connectByObject + realColumn(column));

        return this;
    }

    public DASqlQuery connectByPrior(Object value) {
        return connectByPrior(CONNECT_BY_PRIOR, value);
    }

    public DASqlQuery startWith(Object value) {
        return connectByPrior(START_WITH, value);
    }

    public DASqlQuery connectByIsleaf() {
        return getWhereObject(CONNECT_BY_ISLEAF);
    }

    //---------------------------------------------------------------
    private DASqlQuery join(String joinObject, Object table) {
        objectPosition = JOIN;

        saveTables(table);

        String join = getTableNameFromObject(table);
        putInStringPosition(joinObject + join);

        return this;
    }

    public DASqlQuery innerJoin(Object table) {
        return join(INNER_JOIN, table);
    }

    public DASqlQuery leftOuterJoin(Object table) {
        return join(LEFT_JOIN, table);
    }

    public DASqlQuery rightOuterJoin(Object table) {
        return join(RIGHT_JOIN, table);
    }

    public DASqlQuery on(Object column) {
        putInStringPosition(ON + realColumn(column));

        return this;
    }
//-----------------------------------------------------------
    private DASqlQuery collectiveMethod(String objectPosition, Object...columns){
        this.objectPosition = objectPosition;

        String collective = realValues(columns);

        putInStringPosition(objectPosition + collective);

        return this;
    }

    public DASqlQuery groupBy(Object...columns) {
        return collectiveMethod(GROUP_BY, columns);
    }

    public DASqlQuery having(Object column) {
        return conditionClause(HAVING, column);
    }

    public DASqlQuery orderBy(Object... columns) {
        return collectiveMethod(ORDER_BY, columns);
    }
//---------------------------------------------
    private DASqlQuery collectionMethod(String collectionPosition, DASqlQuery value){
        String result = NEWLINE + NEWLINE + collectionPosition + NEWLINE + realValue(value).toString();

        collectionMethods.add(result);

        return this;
    }

    public DASqlQuery union(DASqlQuery value) {
        return collectionMethod(UNION, value);
    }

    public DASqlQuery unionAll(DASqlQuery value) {
        return collectionMethod(UNION_ALL, value);
    }

    public DASqlQuery intersect(DASqlQuery value) {
        return collectionMethod(INTERSECT, value);
    }

    public DASqlQuery minus(DASqlQuery value) {
        return collectionMethod(MINUS, value);
    }

//--------------------------------------------------------------
    //TODO: this method should similar 'from' method
    private String generateSelect() {

        String select = this.usedDistinct ? DISTINCT : "";

        for(Object column: this.columns){
            InputObject inputObject = (InputObject) column;
//
//            String tableName = realTableName(inputObject);
//
            String alias = (inputObject.alias == null) ? "" : AS + inputObject.alias;
//
//            String columnName = tableName + inputObject.object.toString();

            String columnName = realColumn(column);

            columnName = (inputObject.functionName != "") ? inputObject.functionName + addParenthesis(columnName) : columnName;

            select += columnName  + alias + COMMA ;


            //TODO: throw exception if ObjectType is QUERY and alias is null
            String fieldName = (inputObject.getObjectType() == ObjectType.QUERY) ? inputObject.alias : inputObject.object.toString();
            String realColumn = (alias != "") ? inputObject.alias : inputObject.getRealColumnName();
            this.realFieldNames.put(realColumn.toLowerCase(), fieldName);
        }

        return SELECT + StringUtil.removeLast(select);
    }

    private String realResult(Object result){
        return (result == null) ? "" : NEWLINE + StringUtil.nvl(result);
    }

    private String getCollectionMethods(){
        String result = "";

        for (String method: collectionMethods){
            result += method;
        }

        return ("".equals(result.trim())) ? "" : result;
    }

    public String getQuery() {
         return generateSelect()
                 + realResult(objectQuery.get(FROM))
                 + realResult(objectQuery.get(JOIN))
                 + realResult(objectQuery.get(WHERE))
                 + realResult(objectQuery.get(GROUP_BY))
                 + realResult(objectQuery.get(HAVING))
                 + realResult(objectQuery.get(ORDER_BY))
                 + realResult(objectQuery.get(CONNECT_BY_PRIOR))
                 + getCollectionMethods();
    }

    public SQLQuery executeQuery(){
        String query = getQuery();
        return session.createSQLQuery(query);
    }

//    private <T> Object getColumnValue(ResultSet resultSet, String fieldTypeName, String columnName, String fieldName) throws SQLException {
//        Object columnValue = null;
//        //TODO: add other type
//        switch (fieldTypeName){
//            case "Long":
//                columnValue = resultSet.getLong(columnName);
//                break;
//            case "Integer":
//                columnValue = resultSet.getInt(columnName);
//                break;
//            case "BigDecimal":
//                columnValue = resultSet.getBigDecimal(columnName);
//                break;
//            case "Boolean":
//                columnValue = resultSet.getBoolean(columnName);
//                break;
//            case "Double":
//                columnValue = resultSet.getDouble(columnName);
//                break;
//            case "Float":
//                columnValue = resultSet.getFloat(columnName);
//                break;
//            case "Short":
//                columnValue = resultSet.getShort(columnName);
//                break;
//            case "Byte":
//                columnValue = resultSet.getByte(columnName);
//                break;
//            case "String":
//                columnValue = resultSet.getString(columnName);
//                break;
//            default:
//                columnValue = resultSet.getObject(columnName);
//        }
//
//        return columnValue;
//    }

    public <T> T getSingleResult(){
        SQLQuery result = executeQuery();
        List<T> resultList = result.list();
        return resultList.get(0);
    }

//    public <T> T getSingleResult(){
//        return (T) getSingleResult(null);
//    }

//    public <T> T getSingleResult(Class<T> entityClass){
//        return getListResult(entityClass).get(0);
//    }


    public <T> List<T> getListResult(/*Class<T> entityClass*/){
//        List<T> resultList = new ArrayList<T>();

//        try {
//                ResultSet resultSet = executeQuery();
//                ResultSetMetaData metaData = resultSet.getMetaData();
//                int columnCount = metaData.getColumnCount();
//
//                while (resultSet.next()) {
//                    T newEntity = (entityClass != null) ? EasyObject.newInstance(entityClass) : null;
//                    for (int i = 1; i <= columnCount; i++) {
//                        String columnName = metaData.getColumnName(i);
//                        Object columnValue = resultSet.getObject(columnName);
//                        if (entityClass == null) {
//                            resultList.add((T) columnValue);
//                        }
//                        else {
//                            String fieldName = realFieldNames.get(columnName.toLowerCase());
//                            //TODO: RowNum Do not forget-- last column
//                            if (fieldName != null) {
//                                Class fieldType = EasyObject.getFieldType(entityClass, fieldName);
//                                if (fieldType != null) {
//                                    String fieldTypeName = fieldType.getSimpleName();
//                                    columnValue = getColumnValue(resultSet, fieldTypeName, columnName, fieldName);
//                                    EasyObject.setSetterMethodValue(newEntity, fieldName, columnValue);
//                                }
//                            }
//                        }
//                    }
//
//                    resultList.add((T) newEntity);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

        SQLQuery result = executeQuery();
        return result.list();
    }
}
