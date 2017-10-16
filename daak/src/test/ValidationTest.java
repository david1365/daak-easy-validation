package test;


import da.codes.SMSLabelCode;
import da.dto.ValidateMessageDTO;
import da.generality.StringUtil;
import static da.validation.Validation.*;

import da.validation.BusinessValidation;
import da.enums.ValidationType;

import da.validation.Validation;

import org.junit.Test;
import test.entity.CustomerGroup;
import da.exception.ProcessException;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

/**
 * Created by Akbari.David on 5/7/2017.
 */
public class ValidationTest {

    @Test
    public void itemsNotNullTest(){
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("1");
//        list.add(null);
//        list.add("1");
//
//        assertFalse(validation().itemsIsNotNull(list).isValid());
//
//        List<String> list2 = new ArrayList<>();
//        list.add("1");
//        list.add("1");
//        assertTrue(validation().itemsIsNotNull(list2).isValid());
    }

    @Test
    public void validationPublic(){
        System.out.println(">>>--validationPublic--------------------------------------------------<<<");

//        assertTrue(validation().isNotNull("textTest")
//                .andValidate(BusinessValidation::validateString).isValid());
//
//        assertEquals("text12", validation().isNotNull("textTest")
//                 .andValidate(BusinessValidation::validateString).ifValid(()->{
//
//                    return  "text12";
//                }).getIfResult());
//
//
//        assertEquals("text12", validation().isNotNull("123text")
//                .andValidate(BusinessValidation::validateString)
//                .andNegateValidate(BusinessValidation::isNumber).ifValid(()->{
//
//                    return  "text12";
//                }).getIfResult());
//
//
////        assertTrue(validation().isTrue(true).andValue(true).isValid());
////        assertTrue(validation().isNotNull(null).orValue("text1").isValid());
////
////        assertFalse(validation().isNotNull(null).andValue("text1").isValid());
////        assertFalse(validation().isTrue(true).andValue(false).isValid());
//
//        assertFalse(validation().val(null).validate(BusinessValidation::validateString).andValue("text").isValid());
//        assertTrue(validation().val(null).validate(BusinessValidation::validateString).orValue("text").isValid());
//
//        assertTrue(validation().val(null).validate(BusinessValidation::validateString).orValue("text").andValue("text24").andValue("text55").isValid());
//        assertFalse(validation().val(null).validate(BusinessValidation::validateString).orValue("text").andValue("text24").andValue("").isValid());
//
//
//       assertEquals("text12", validation().val(null).validate(BusinessValidation::validateString).orValue("text").ifValid(()->{
//
//            return  "text12";
//        }).getIfResult());
//
//
//        assertEquals("text12", validation().val("text1").validate(BusinessValidation::validateString).andValue("text").andValue("text").ifValid(()->{
//
//            return  "text12";
//        }).getIfResult());
//
//
//        assertEquals("text123", validation().val("text1").validate(BusinessValidation::validateString).andValue("")
//                .ifValid(()->{
//
//                    return  "text12";
//                })
//                .ifNotValid(()-> "text123")
//                .getIfResult());
//
//
//        assertEquals("correct", validation().val("text1").validate(BusinessValidation::validateString).andValue("").orValue("textOr")
//                .ifValid(()->{
//
//                    return  "correct";
//                })
//                .ifNotValid(()-> "text123")
//                .getIfResult());



//        assertTrue(validation().val(150).min(100).isValid());
//        assertFalse(validation().val(98).min(100).isValid());
//
//        assertTrue(validation().val(50).max(100).isValid());
//        assertFalse(validation().val(150).max(100).isValid());
//
//        assertTrue(validation().val(100).max(100).isValid());
//        assertTrue(validation().val(100).min(100).isValid());
//
//        assertTrue(validation().val(100, 150).min().isValid());
//        assertFalse(validation().val(100, 98).min().isValid());
//
//        assertTrue(validation().val(100, 50).max().isValid());
//        assertFalse(validation().val(100, 150).max().isValid());
//        //--
//        assertFalse(validation().val(100, 50).equals().isValid());
//        assertTrue(validation().val(100, 100).equals().isValid());
//
//        assertFalse(validation().val(100).equals(50).isValid());
//        assertTrue(validation().val(100).equals(100).isValid());
//
//        assertFalse(validation().equals(100, 50).isValid());
//        assertTrue(validation().equals(100, 100).isValid());
//        //--
//        assertTrue(validation().val(100, 50).isNotEquals().isValid());
//        assertFalse(validation().val(100, 100).isNotEquals().isValid());
//
//        assertTrue(validation().val(100).isNotEquals(50).isValid());
//        assertFalse(validation().val(100).isNotEquals(100).isValid());
//
//        assertTrue(validation().isNotEquals(100, 50).isValid());
//        assertFalse(validation().isNotEquals(100, 100).isValid());
//        //--
//        assertFalse(validation().val(100, 50).greaterThanOrEquals().isValid());
//        assertTrue(validation().val(100, 100).greaterThanOrEquals().isValid());
//        assertFalse(validation().val(100).greaterThanOrEquals(150).isValid());
//        assertTrue(validation().val(100).greaterThanOrEquals(100).isValid());
//        assertFalse(validation().greaterThanOrEquals(100, 50).isValid());
//        assertTrue(validation().greaterThanOrEquals(100, 100).isValid());
//
//        assertTrue(validation().greaterThanOrEquals(100, 150).isValid());
//        assertTrue(validation().val(100, 150).greaterThanOrEquals().isValid());
//        assertTrue(validation().val(150).greaterThanOrEquals(100).isValid());
//
//        //--
//        assertFalse(validation().val(100, 50).greaterThan().isValid());
//        assertFalse(validation().val(100, 100).greaterThan().isValid());
//
//        assertFalse(validation().val(100).greaterThan(150).isValid());
//        assertFalse(validation().val(100).greaterThan(100).isValid());
//
//        assertFalse(validation().greaterThan(100, 50).isValid());
//        assertFalse(validation().greaterThan(100, 100).isValid());
//
//        assertTrue(validation().greaterThan(100, 150).isValid());
//        assertTrue(validation().val(100, 150).greaterThan().isValid());
//        assertTrue(validation().val(150).greaterThan(100).isValid());
//        //--
//
//        assertTrue(validation().val(100, 50).lessThan().isValid());
//        assertFalse(validation().val(100, 100).lessThan().isValid());
//
//        assertTrue(validation().val(100).lessThan(150).isValid());
//        assertFalse(validation().val(100).lessThan(100).isValid());
//
//        assertTrue(validation().lessThan(100, 50).isValid());
//        assertFalse(validation().lessThan(100, 100).isValid());
//
//        assertFalse(validation().lessThan(100, 150).isValid());
//        assertFalse(validation().val(100, 150).lessThan().isValid());
//        assertFalse(validation().val(150).lessThan(100).isValid());
//
//        //--
//        assertTrue(validation().val(100, 50).lessThanOrEquals().isValid());
//        assertTrue(validation().val(100, 100).lessThanOrEquals().isValid());
//
//        assertTrue(validation().val(100).lessThanOrEquals(150).isValid());
//        assertTrue(validation().val(100).lessThanOrEquals(100).isValid());
//
//        assertTrue(validation().lessThanOrEquals(100, 50).isValid());
//        assertTrue(validation().lessThanOrEquals(100, 100).isValid());
//
//        assertFalse(validation().lessThanOrEquals(100, 150).isValid());
//        assertFalse(validation().val(100, 150).lessThanOrEquals().isValid());
//        assertFalse(validation().val(150).lessThanOrEquals(100).isValid());
//        //--
//
//        assertTrue(validation().val(true).isTrue().isValid());
//        assertFalse(validation().isTrue(false).isValid());
//        assertFalse(validation().isTrue(null).isValid());
//
//        assertTrue(validation().val("text").isText().isValid());
//        assertTrue(validation().val(123).isNumber().isValid());
//        assertTrue(validation().val(true).isBoolean().isValid());
//        assertTrue(validation().val(false).isBoolean().isValid());
//        assertTrue(validation().isNotNull(false).isBoolean().isValid());
//
//        assertFalse(validation().val(123).isText().isValid());
//        assertFalse(validation().val("text").isNumber().isValid());
//        assertFalse(validation().val("text").isBoolean().isValid());
//        assertFalse(validation().val(123).isBoolean().isValid());
//        assertFalse(validation().val(false).isNumber().isValid());
//
//
//        assertFalse(validation().val(false).isNumber().ifValid().isNotNull().isValid());
//        assertTrue(validation().val(false).isBoolean().ifValid().isNotNull().isValid());
//        assertTrue(validation().isNotNull(false).ifValid().isBoolean().isValid());
////--

////--
//
//        assertFalse(validation().isNotNull("").ifValid().isBoolean().isValid());
//        assertFalse(validation().val(false).isNumber().ifValid().validate(BusinessValidation::validateString).isValid());
//
//        validation().val(false).isNumber().ifValid().throwValidate();
//
//        try {
//            String bool = "text";
//            validation().isNotNull(bool).isBoolean().throwValidate();
//
//            fail( "Fail to ThrowException------------------validationPublic" );
//        } catch (ProcessException expectedException) {
//        }
    }

    @Test
    public void validationTest(){
//        System.out.println(">>>--validationTest--------------------------------------------------<<<");
//        try {
//            //--take---------------------------------------------------------
//            assertEquals(ValidationType.GENERAL_COLLECTION_NULL, validation().val(new ArrayList()).take(BusinessValidation::validateCollection).getValidationType());
//
//            //--throwValidate--------------------------------------------------
//            String str = null;
//            validation().val(null).throwValidate(BusinessValidation::validateCollection);
//
//            fail( "Fail to ThrowException" );
//        } catch (ProcessException expectedException) {
//        }
//
//
//        assertTrue(validation().isNotNull("text").isValid());
//
    }
//-----------------------------------------------------------------
    @Test
    public void isNullTest(){
//        String str = null;
//        assertFalse(validation().isNotNull(str).isValid());
//
//        //-------------------------------------------------
//         str = "";
//        assertTrue(validation().isNotNull(str).isValid());
//        //--------------------------------------------------
//
//        str = null;
//        assertEquals("david", validation().isNotNull(str).<String>ifNotValid("david").getIfResult());


        assertFalse(validation().val("123").isNull().isValid());
        assertFalse(validation().isNull(123).isValid());

        assertFalse(validation().val("123", 24, null).isNull().isValid());
        assertFalse(validation().isNull(123, 22, null).isValid());

        assertTrue(validation().val(null).isNull().isValid());
        assertTrue(validation().isNull(null).isValid());

        assertTrue(validation().val(null, null, null, null).isNull().isValid());
        assertTrue(validation().isNull(null, null, null, null).isValid());

        assertFalse(validation().val(null, 122, 225, null).isNull().isValid());
        assertFalse(validation().val(12, 55, 66, null).isNull().isValid());

        assertFalse(validation().isNull(null, 123).isValid());
        assertFalse(validation().isNull(12, null).isValid());

        try {
            validation().isNull(123, 22, null).throwValidate();

            fail( "Fail to ThrowException------------------isNull" );
        } catch (ProcessException expectedException) {
        }

        System.out.println(">>>--isNullTest--------------------------------------------------<<<");
    }

    @Test
    public void isNotNullTest() {
        assertTrue(validation().val("123", 123, '1').isNotNull().isValid());
        assertTrue(validation().isNotNull(123, 135, "#33").isValid());

        assertTrue(validation().val("123", 24, "333", "2323").isNotNull().isValid());
        assertTrue(validation().isNotNull(123, 22, 44, 55).isValid());

        assertFalse(validation().val(null, null, null, null).isNotNull().isValid());
        assertFalse(validation().isNotNull(null, null, null).isValid());

        assertFalse(validation().val(null, null, 123).isNotNull().isValid());
        assertFalse(validation().isNotNull(null, null, 555).isValid());

        assertFalse(validation().val(null, 122, 99).isNotNull().isValid());
        assertFalse(validation().val(12, null, 345).isNotNull().isValid());

        assertFalse(validation().isNotNull(null, 123, "44").isValid());
        assertFalse(validation().isNotNull(12, null, 554).isValid());

        try {
            validation().isNotNull(123, 22, null).throwValidate();

            fail("Fail to ThrowException------------------isNotNull");
        } catch (ProcessException expectedException) {
        }

        System.out.println(">>>--isNotNullTest--------------------------------------------------<<<");
    }

    @Test
    public void isTextTest() {
        assertTrue(validation().val("text").isText().isValid());
        assertFalse(validation().val(123).isText().isValid());
        assertFalse(validation().isText(123,"12").isValid());
        assertFalse(validation().val(true, "12").isText().isValid());
        assertFalse(validation().val("33", "12").isText().isValid());
        assertTrue(validation().val("david", "ali").isText().isValid());
        assertTrue(validation().isText("t", "ali").isValid());

        try {
            validation().val(123, "text").isText().throwValidate();

            fail("Fail to ThrowException------------------isText");
        } catch (ProcessException expectedException) {
        }
    }

    @Test
    public void isNumberTest() {
        assertFalse(validation().val("text").isNumber().isValid());
        assertTrue(validation().val(123).isNumber().isValid());
        assertTrue(validation().isNumber(123,"12").isValid());
        assertFalse(validation().val(true, "12").isNumber().isValid());
        assertTrue(validation().val("33", "12").isNumber().isValid());
        assertFalse(validation().val("david", "ali").isNumber().isValid());
        assertFalse(validation().isNumber("t", "ali").isValid());

        try {
            validation().val(123, "text").isNumber().throwValidate();

            fail("Fail to ThrowException------------------isNumber");
        } catch (ProcessException expectedException) {
        }
    }

    @Test
    public void isBooleanTest() {
        assertFalse(validation().val("text").isBoolean().isValid());
        assertTrue(validation().val(true).isBoolean().isValid());
        assertTrue(validation().isBoolean(true, false).isValid());
        assertFalse(validation().val(true, "12").isBoolean().isValid());
        assertTrue(validation().val(true, true, false).isBoolean().isValid());
        assertFalse(validation().val("david", "ali").isBoolean().isValid());
        assertFalse(validation().isBoolean("t", "ali").isValid());

        try {
            validation().val(123, "text").isBoolean().throwValidate();

            fail("Fail to ThrowException------------------isBoolean");
        } catch (ProcessException expectedException) {
        }
    }

    public static ValidateMessageDTO supplierValidateTest() {
        return new ValidateMessageDTO(ValidationType.GENERAL_OBJECT_NULL);
    }

    @Test
    public void validationIfValidTest(){
//        System.out.println(">>>--validationIfValidTest--------------------------------------------------<<<");
//         String st = null;
//
//        String str1  = validation().val(st)
//                .validate(BusinessValidation::validateString
//                ).<String>ifValid(()-> "text")
//                .ifNotValid(123)
//                .setAsValue()
//                .<String>isText()
//                .<String, String>ifValid((s)-> s + " text2")
//                .ifNotValid("")
//                .getIfResult();
//
//        assertEquals("", str1);
//
//         str1  = validation().val(st)
//                .validate(BusinessValidation::validateString
//                ).<String>ifValid(()-> "text")
//                .<String, String>ifValid((s)-> s + " text2")
//                .ifNotValid((value)-> value)
//                .getIfResult();
//
//        assertEquals(null, str1);
////---------------------------------------------
//        str1  = validation().val(st)
//                .validate(BusinessValidation::validateString
//                ).<String>ifValid(()-> "text")
//                .ifNotValid(123)
//                .setAsValue()
//                .<String>isNumber()
//                .<Integer, String>ifValid((s)-> s.toString() + " text2")
//                .ifNotValid("")
//                .getIfResult();
//
//        assertEquals("123 text2", str1);
//
//
////--ifValidFuncion---------------------------------------------------
//        String str  = validation().val(st).validate(BusinessValidation::validateString).<String>ifValid(()-> "text0")
//                .ifNotValid("text1").setAsValue().<String>validate(BusinessValidation::validateString).<String, String>ifValid((s)-> s + " text2").getIfResult();
//
//        assertEquals("text1 text2", str);
////-----------------------------------------------------------------------
//        str  = validation().val(st).validate(BusinessValidation::validateString).<String>ifValid(()->
//                "text"
//        ).getIfResult();
//
//        assertEquals(null, str);
////-----------------------------------------------
//         str  = validation().val(st).validate(BusinessValidation::validateString).<String>ifValid(()->
//               "text"
//         ).ifNotValid("text2").getIfResult();
//
//        assertEquals("text2", str);
////---setAsValue-------------------------------------------
//        str  = validation().val(st).validate(BusinessValidation::validateString).<String>ifValid(()->
//                "text"
//        ).setAsValue().validate(BusinessValidation::validateString).ifNotValid("text2").getIfResult();
//
//        assertEquals("text2", str);
////--------------------------------------------
//        st = "david";
//        str  = validation().val(st).validate(BusinessValidation::validateString).<String>ifValid(()->
//                "text"
//        ).ifNotValid(()-> "").getIfResult();
//
//        assertEquals("text", str);
//        //---validate supplier---------------------------------------
//        try {
//            // String str = null;
//            validation().throwValidate(ValidationTest::supplierValidateTest);
//
//            fail( "Fail to ThrowException ---------<<<<<<--validate supplier-->>>>>" );
//        } catch (ProcessException expectedException) {
//        }
//
//        //--validateCollection------------------------------------------
//        try {
//            // String str = null;
//            validation().val(new ArrayList<>()).collectionIsEmpty().throwValidate();
//
//            fail( "Fail to ThrowException ---------<<<<<<--validateCollection-->>>>>" );
//        } catch (ProcessException expectedException) {
//        }
//
//        //--collectionSingleSingleRow------------------------------------------
//        try {
//             List list = new ArrayList<String>(){{
//                add("text");
//                add("text");
//             }} ;
//             validation().val(list).collectionSingleRow().throwValidate();
//
//            fail( "Fail to ThrowException ---------<<<<<<--collectionSingleRow-->>>>>" );
//        } catch (ProcessException expectedException) {
//        }
    }
    public static ValidateMessageDTO throwValidateTest(){
        return new ValidateMessageDTO(ValidationType.GENERAL_OBJECT_NULL);
    }

    @Test
    public void dublicateEntityTest(){
//        System.out.println(">>>--dublicateEntityTest--------------------------------------------------<<<");
//
//        ArrayList<CustomerGroup> customerGroups = new ArrayList<>();
//        CustomerGroup customerGroup = new CustomerGroup();
//        CustomerGroup customerGroup2 = new CustomerGroup();
//        CustomerGroup customerGroup3 = new CustomerGroup();
//
//        customerGroup.setTitle("text");
//        customerGroup2.setTitle("text");
//        customerGroup3.setTitle("text");
//
//        customerGroups.add(customerGroup);
//        customerGroups.add(customerGroup2);
//        customerGroups.add(customerGroup3);
//
//        try {
//           // String str = null;
//            validation().val(customerGroups, CustomerGroup.FieldNames.TITLE).throwValidate(BusinessValidation::checkUnique)
//            .throwValidate(ValidationTest::throwValidateTest);
//
//            fail( "Fail to ThrowException" );
//        } catch (ProcessException expectedException) {
//        }
//
//        try {
//            validation().val(customerGroups, CustomerGroup.FieldNames.TITLE).checkUnique().throwValidate();
//
//            fail( "Fail to ThrowException" );
//        } catch (ProcessException expectedException) {
//        }
//
//        try {
//            validation().val(customerGroups).checkUnique(CustomerGroup.FieldNames.TITLE).throwValidate();
//
//            fail( "Fail to ThrowException" );
//        } catch (ProcessException expectedException) {
//        }
//
//        try {
//            validation().checkUnique(customerGroups, CustomerGroup.FieldNames.TITLE).throwValidate();
//
//            fail( "Fail to ThrowException" );
//        } catch (ProcessException expectedException) {
//        }

    }

    @Test
    public void upperFirstTest(){
        assertEquals("Java", StringUtil.upperFirst("java"));

    }

    public static ValidateMessageDTO testValidation(String value){
        return valid();
    }

    @Test
    public void executeValidationTest(){
//        String validationName = "test";
//
//        assertEquals("GENERAL_VALID", validation()
//                            .addValidation(validationName, ValidationTest::testValidation, String.class)
//
//                            .executeValidation(validationName, "\"124\"").toString());
//
//        try {
//            validation()
//                    .addValidation(validationName, ValidationTest::testValidation, String.class)
//
//                    .executeValidation("", "124");
//
//
//            fail( "Fail to ThrowException----- executeValidationTest" );
//        } catch (ProcessException expectedException) {
//            assertEquals("GENERAL_NOT_THE_METHOD_NAME", expectedException.getMessage());
//        }

    }

    @Test
    public void runnableTest(){
//        class Test{
//            public int id = 0;
//        }
//        Test t = new Test();
//
//        validation().isNotNull(null)
//                .ifNotValid(()-> {
//                    t.id = 10;
//                    assertEquals(10,t.id);
//                });
//
//        validation().isNotNull(1)
//                .ifValid(()-> {
//                    t.id = 11;
//                    assertEquals(11,t.id);
//                });
    }

    @Test
    public void checkUniqueValueTest(){
//        List<String> list = new ArrayList<>();
//        list.add("0");
//        list.add(null);
//        list.add("0");
//
//        try {
//            Validation uniqe = validation();
//            for (String s : list){
//                uniqe.isNotNull(s).throwValidate()
//                        .ifValid()
//                        .checkUniqueValue(s).throwValidate();
//            }
//
//
//            fail( "Fail to ThrowException----- checkUniqueValueTest" );
//        } catch (ProcessException expectedException) {
//            assertEquals("GENERAL_VALUE_IS_NULL", expectedException.getMessage());
//        }
//
//        list = new ArrayList<>();
//        list.add("0");
//        list.add(null);
//        list.add("0");
//
//        try {
//            Validation uniqe = validation();
//            for (String s : list){
//                uniqe.isNotNull(s)
//                        .ifValid()
//                        .checkUniqueValue(s)
//                        .ifNotValid()
//                        .throwAllValidate();
//            }
//
//
//            fail( "Fail to ThrowException----- checkUniqueValueTest" );
//        } catch (ProcessException expectedException) {
//            assertEquals("* GENERAL_VALUE_IS_NULL", expectedException.getMessage());
//        }
//
//
//
//        list = new ArrayList<>();
//        list.add("0");
//        list.add("0");
//        list.add("0");
//
//        try {
//            Validation uniqe = validation();
//            for (String s : list){
//                uniqe.isNotNull(s).throwValidate()
//                .ifValid()
//                .checkUniqueValue(s).throwValidate();
//            }
//
//
//            fail( "Fail to ThrowException----- checkUniqueValueTest" );
//        } catch (ProcessException expectedException) {
//            assertEquals("GENERAL_VALUE_OF_ENTITY_IS_DUPLICATED", expectedException.getMessage());
//        }
//
//        list = new ArrayList<>();
//        list.add("0");
//        list.add("0");
//        list.add("0");
//
//        try {
//            Validation uniqe = validation();
//            for (String s : list){
//                uniqe.isNotNull(s).throwValidate()
//                        .ifValid()
//                        .checkUniqueValue().throwValidate();
//            }
//
//
//            fail( "Fail to ThrowException----- checkUniqueValueTest" );
//        } catch (ProcessException expectedException) {
//            assertEquals("GENERAL_VALUE_OF_ENTITY_IS_DUPLICATED", expectedException.getMessage());
//        }
    }

    @Test
    public void BundleTest(){
//        new RunnerClass(true).run(session -> {

//            try {
//                validation().isNotNull(null).setThrowBundles(SMSLabelCode.SMS_SALES_ITEM_PRODUCT_GROUPS)
//                        .andValue(null).setThrowBundles(SMSLabelCode.SMS_SALES_ITEM_PRODUCT_GROUP_MAIN_USAGE)
//                        .andValue(null).setThrowBundles(SMSLabelCode.SMS_SALES_ITEM_PRODUCT_GROUP_PRICING_USAGE)
//                        .isTrue(false)
//                        .isNotNull(null)
//                        .isTrue(true)
//                        .val(3).max(4)
//                        .val(10).max(4)
//                        .val(4).min(1)
//                        .val(-1).min(1)
//                        .val("").validate(BusinessValidation::validateString)
//                        .val(null).andValidate(BusinessValidation::validateString).setThrowBundles(SMSLabelCode.SMS_SALES_ITEM_PRODUCT_GROUPS)
//                        .throwAllValidate();
//
//
//                fail( "Fail to ThrowException----BundleTest" );
//            } catch (ProcessException expectedException) {
//// todo: resolve this block
////    String msg = "* فیلد \"گروه محصولات\" می بایستی پر گردد.<br><br>* فیلد \"گروه محصولات نوع کاربرد اصلی\" می بایستی پر گردد.<br><br>* فیلد \"گروه محصولات نوع کاربرد قیمت گذاری\" می بایستی پر گردد.<br><br>* مقدار وارد شده منفی است!<br><br>* فیلد \"{0}\" می بایستی پر گردد.<br><br>* مقدار \"{0}\" بزرگتر از \"{1}\" است!<br><br>* مقدار \"{0}\" کوچکتر از \"{1}\" است!<br><br>* فیلد \"{0}\" می بایستی پر گردد.<br><br>* فیلد \"گروه محصولات\" می بایستی پر گردد.";
//                String msg = "* GENERAL_VALUE_IS_NULL<br><br>* GENERAL_VALUE_IS_NULL<br><br>* GENERAL_VALUE_IS_NULL<br><br>* GENERAL_VALUE_IS_FALSE<br><br>* GENERAL_VALUE_IS_NULL<br><br>* GENERAL_VALUE_IS_MAX<br><br>* GENERAL_VALUE_IS_MIN<br><br>* GENERAL_VALUE_IS_NULL<br><br>* GENERAL_VALUE_IS_NULL";
//                assertEquals(msg, expectedException.getMessage());
//            }
////        });
    }
}
