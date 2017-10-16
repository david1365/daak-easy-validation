package test; /**
 * Created by david on 5/13/17.
 */

import classes.ExternalJobTypeEntity;
import da.sql.DASqlQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static da.sql.DASqlQuery.*;

public class QueryTest {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("resource/hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    @BeforeClass
    public static void setSession(){
        final Session session = getSession();
        DASqlQuery.setSession(session);
    }



    @Test
    public void simpleSelect(){
        DASqlQuery query =
                select(ExternalJobTypeEntity.CODE)
                .from(ExternalJobTypeEntity.class);

        BigDecimal code = (BigDecimal) query.getListResult().get(0);
        assertEquals(BigDecimal.valueOf(44), code);
    }

    @Test
    public void aggregateFunctions(){
        DASqlQuery query = select(count(ExternalJobTypeEntity.CODE).as("david"))
                           .from(ExternalJobTypeEntity.class)
                           ;//.where(ExternalJobTypeEntity.ID).equal("44");
        //System.out.println(query.getQuery());

        BigDecimal count = query.getSingleResult();
        assertEquals(BigDecimal.valueOf(1), count);
    }



//    @Test
//    public void connectByPriorTest(){
//        final Session session = getSession();
//        session.beginTransaction();
//            //---Start---------------------------------------------------------
//
//            DASqlQuery query =
//                    select(count(SalesItemTree.FieldNames.ID))
//                    .from(SalesItemTree.class)
//                    .where(SalesItemTree.FieldNames.ID).equal(150)
//                    .and().connectByIsleaf().equal(1)
//                    .connectByPrior(SalesItemTree.FieldNames.ID).equal(SalesItemTree.FieldNames.PARENT);
//
//            BigDecimal count = query.getSingleResult();
//
//            System.out.println("----------------------------------------------------------");
//            System.out.println("count: " + count);
//            System.out.println("----------------------------------------------------------");
//
//            //--END------------------------
//            session.commitTransaction();
//            session.close();
//    }

//    public static void main(String[] args) {



//---Start---------------------------------------------------------

//        String query =
//                select(
//                        //distinct(CustomerGroup.FieldNames.ID, $(CustomerGroup.FieldNames.ACTIVE).as("activeeeeee"))
//                        _("customer1").$(CustomerGroup.FieldNames.ID).as("c1"),
//                        _("cust2").$(CustomerGroup.FieldNames.ID).as("david_id"),
//                        $(CustomerGroup.FieldNames.INSERT_TIME),
//                        CustomerGroup.FieldNames.TITLE,
//                        $(select(CustomerGroup.FieldNames.ID, CustomerGroup.FieldNames.CODE)).as("customer1"),
//                        CustomerGroup.FieldNames.DESCRIPTION,
//                        select(CustomerGroup.FieldNames.ACTIVE, CustomerGroup.FieldNames.VERSION)
//                )
//                .from(CustomerGroup.class, $(CustomerGroup.class).as("cust2"))
//                .where(_("customer1").$(CustomerGroup.FieldNames.ID)).equal(12)
//                .and()
//                .getQuery();

//        String query =
//                select(
//                        distinct(
////                                $("customer1", CustomerGroup.FieldNames.ID).as("c1"),
////                                $("alki", CustomerGroup.FieldNames.ID),
////
//                                _("cg").$(CustomerGroup.FieldNames.ACTIVE).as("activeeeeee")//,
//
//                               // CustomerGroup.FieldNames.ID
//
////
//                        )
//                )
//                .from(
////                        $(CustomerGroup.class).as("cust3"),
//                        $(CustomerGroup.class).as("cg")//,
////
////
////                                $(select(CustomerGroup.FieldNames.ID, CustomerGroup.FieldNames.CODE)
////                                        .from($(CustomerGroup.class).as("c1"))
////                                ).as("customer1")
//                ).join($(Customer.class).as("cus")).on(_("cg").$(CustomerGroup.FieldNames.ACTIVE)).equal(_("cus").$(CustomerGroup.FieldNames.ACTIVE))
//                .where(_("cus").$(CustomerGroup.FieldNames.ID)).equal(12)
//                        .and(_("ter").$(CustomerGroup.FieldNames.ACTIVE)).equal("acitive")
//                        .and().not(_("ter").$(CustomerGroup.FieldNames.ASSURANCE_PERCENT)).equal(55)
//                        .or().not(_("customer1").$(CustomerGroup.FieldNames.ID)).equal("gr")
//                .getQuery();

//            String query =
//                    select(
//                            distinct(
//                                    $(CustomerGroup.FieldNames.ACTIVE).as("activeeeeee"),
//                                    $(Customer.FieldNames.CODE).as("code1")
////                            ,
////                        $(select(CustomerGroup.FieldNames.CODE, Customer.FieldNames.DESCRIPTION)
////                                    .from($(CustomerGroup.class).as("c1"), $(Customer.class)))
//                            )
//                    )
//                            .from(
//                                    $(CustomerGroup.class).as("cg")
//                            )
//                            .innerJoin($(Customer.class).as("cus")).on($(CustomerGroup.FieldNames.ID)).equal($(Customer.FieldNames.ID))
//                            .leftOuterJoin($(select(CustomerGroup.FieldNames.CODE)
//                                    .from($(CustomerGroup.class).as("c1"))
//                            )).on(_("c1").$(CustomerGroup.FieldNames.ACTIVE)).equal(_("cus").$(Customer.FieldNames.ACTIVE))
//                            .where($(CustomerGroup.FieldNames.TITLE)).equal("mamad").and(CustomerGroup.FieldNames.CODE).not().in(1,2,3)
//                            .and(CustomerGroup.FieldNames.ACTIVE).notIn($(select(CategoryElement.FieldNames.CODE)
//                            .from($(CategoryElement.class).as("cat1"))) , 1,2,3).and()
//                            .notExists(select(Customer.FieldNames.ACTIVE)
//                                    .from($(Customer.class).as("customer1"))
//                                    .where(_("customer1").$(Customer.FieldNames.CODE)).equal(_("cg").$(CustomerGroup.FieldNames.CODE)))
//                            .groupBy($(Customer.FieldNames.CODE), CustomerGroup.FieldNames.ACTIVE)
//                            .having(CustomerGroup.FieldNames.ID).equal(55)
//                            .orderBy(CustomerGroup.FieldNames.TITLE, $(CustomerGroup.FieldNames.ID).desc(), $(CustomerGroup.FieldNames.DESCRIPTION).asc())
//                            .union(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//                            .union(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//                            .union(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//                            .unionAll(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//                            .unionAll(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//                            .intersect(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//
//                            .intersect(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//
//                            .minus(select(CategoryElement.FieldNames.CODE)
//                                    .from(CategoryElement.class))
//
//                            .minus(select(sum(CategoryElement.FieldNames.CODE).as("sum_cod"), count(CategoryElement.FieldNames.ACTIVE))
//                                    .from(CategoryElement.class))
//
//                            .getQuery();

            //COUNT
//            DASqlQuery query =
//                    select(
//                        count(CustomerGroup.FieldNames.ID).as("count")
//                    )
//                    .from($(CustomerGroup.class).as("cusg"));
//
//            System.out.println(query.getQuery());
//            System.out.println(query.<Long>getSingleResult());
//
//
//            query =
//                    select(
//                            $(CustomerGroup.FieldNames.ID).as("id")
//                            ,$(CustomerGroup.FieldNames.ACTIVE).as("ACTIVE")
//                            ,$(CustomerGroup.FieldNames.MAX_DISCOUNT_ORDER).as("MAX_DISCOUNT_ORDER")
//                    )
//                    .from($(CustomerGroup.class).as("cusg"));
//
//            CustomerGroup customerGroup = query.<CustomerGroup>getSingleResult(CustomerGroup.class);
//            System.out.println(customerGroup.getId() + "----" + customerGroup.getActive() + "-----" + customerGroup.getMaxDiscountOrder());
//
//            for (CustomerGroup customerGroup1: query.getListResult(CustomerGroup.class)) {
//                System.out.println(customerGroup1.getId() + "----" + customerGroup1.getActive() + "-----" + customerGroup1.getMaxDiscountOrder());
//            }



            //INNER JOIN
//            query =
//                    select(
//                            $(SalesItemTree.FieldNames.ID).as("id")
//                            ,ProductGroup.FieldNames.TITLE
//                    )
//                    .from($(SalesItemTree.class).as("cusg"))
//                    .innerJoin(ProductGroup.class).on(SalesItemTree.FieldNames.PRODUCT_GROUP).equal(ProductGroup.FieldNames.ID);
//
//            List<ProductGroup> productGroupList = query.getListResult(ProductGroup.class);
//
//            System.out.println("---------------------------------------------------");
//            for (ProductGroup productGroup: productGroupList){
//                System.out.println(productGroup.getId() + "|" + productGroup.getTitle());
//            }


            //SELECT IN SELECT
//            query =
//                    select(
//                            $(SalesItemTree.FieldNames.ID).as("id"),
//
//                            $(select(ProductGroupType.FieldNames.TITLE)
//                                    .from(ProductGroupType.class)
//                                    .where(ProductGroupType.FieldNames.ID).equal(_("sales_tree").$(SalesItemTree.FieldNames.TYPE))).as("title")
//                    )
//                    .from($(SalesItemTree.class).as("sales_tree"))
//                    .where(SalesItemTree.FieldNames.ID).equal(123);
//
//            ProductGroupType productGroupType = query.getSingleResult(ProductGroupType.class);

//            System.out.println("---------------------------------------------------");
//
//            System.out.println(productGroupType.getId() + "|" + productGroupType.getTitle());



            //SELECT UNION---------------------------------------------------------
//            class SalesItemTreeTmp extends
//            query =
//                    select(
//                            $(SalesItemTree.FieldNames.ID).as("id"),
//
//                            $(select(ProductGroupType.FieldNames.TITLE)
//                                    .from(ProductGroupType.class)
//                                    .where(ProductGroupType.FieldNames.ID).equal(_("sales_tree").$(SalesItemTree.FieldNames.TYPE))).as("title"),
//
//                            SalesItemTree.FieldNames.PARENT
//                    )
//                            .from($(SalesItemTree.class).as("sales_tree"))
//                    .union(
//                            select(
//                                $(SalesItemTree.FieldNames.ID).as("id"),
//
//                                $(select(ProductGroup.FieldNames.TITLE)
//                                        .from(ProductGroup.class)
//                                        .where(ProductGroup.FieldNames.ID).equal(_("sales_tree1").$(SalesItemTree.FieldNames.PRODUCT_GROUP))).as("title"),
//
//                                    SalesItemTree.FieldNames.PARENT
//                            )
//                            .from($(SalesItemTree.class).as("sales_tree1"))
//                    );
//
//            ProductGroup productGroupAndType = query.getSingleResult(ProductGroup.class);
//            List<ProductGroup> productGroupAndTypeList = query.getListResult(ProductGroup.class);
//
//            System.out.println("---------------------------------------------------");
//
//            System.out.println(productGroupAndType.getId() + "|" + productGroupAndType.getTitle());
//
//            System.out.println("---------------------------------------------------");
//
//            for (ProductGroup productGroup: productGroupAndTypeList){
//                System.out.println(productGroup.getId() + "|" + productGroup.getTitle());
//            }
//          //--END------------------------
//            session.commitTransaction();
//            session.close();
//        });
//    }

//    public final String noSelectedTableObjectMsg = "";

//    @Test
//    public void selectInFromTest(){
//        new RunnerClass(true).run(session -> {
//            session.beginTransaction();
//            //---Start---------------------------------------------------------
//
//           DASqlQuery query =
//                    select(count(_("PRODUCT_GROUPS").$(SalesItemTree.FieldNames.ID)))
//                    .from(
//                            $(select(SalesItemTree.FieldNames.ID, SalesItemTree.FieldNames.PARENT)
//                            .from(SalesItemTree.class)
//                            .where(SalesItemTree.FieldNames.PRODUCT_GROUP).isNotNull()).as("PRODUCT_GROUPS")
//                    );
//
//            Long count = query.getSingleResult();
//
//            System.out.println("----------------------------------------------------------");
//            System.out.println("count: " + count);
//            System.out.println("----------------------------------------------------------");
//
//            //--END------------------------
//            session.commitTransaction();
//            session.close();
//        });
//    }
//
//
//
//    public void throwNoSelectedTableObject(){
//        try{
//
//
//
//            fail( "Fail to ThrowException----throwNoSelectedTableObject" );
//        } catch (ProcessException expectedException) {
//            assertEquals(noSelectedTableObjectMsg, expectedException.getMessage());
//        }
//    }
}
