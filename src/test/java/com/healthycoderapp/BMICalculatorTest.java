package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


class BMICalculatorTest {
    @AfterEach
         void after1(){
            System.out.println("The Test is done done");
        }
    @Test
    public void test1(){
        //given
        double weight = 50.1;
        double height = 0.0;
        //when
        Executable executable =() -> BMICalculator.isDietRecommended(weight,height);
        //then
        Assertions.assertThrows(ArithmeticException.class,executable);
    }

    @Test
    void findCoderWithWorstBMITest() {
        //given
        List <Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.88,60.0));
        coders.add(new Coder(1.82,98.0));
        coders.add(new Coder(1.82,64.0));
        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        //then
        Assertions.assertAll(
                ()->Assertions.assertEquals(1.82,coderWorstBMI.getHeight()),
                ()->Assertions.assertEquals(98.0,coderWorstBMI.getWeight())
        );
    }
    @Test
    void should_return_Null() {
        //given
        List <Coder> coders = new ArrayList<>();
        //when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        //then
        Assertions.assertNull(coderWorstBMI);
        System.out.println("The Test is done");
    }
    @Test
    void BMI_score_Array() {
        //given
        List <Coder> coders = new ArrayList<>();
        coders.add(new Coder( 1.80,60.0));
        coders.add(new Coder(1.82,98.0));
        coders.add(new Coder(1.82,64.7));
        double [] expected={18.52,29.59,19.53};
        //when
        double [] BMI_score_array = BMICalculator.getBMIScores(coders);
        //then
        Assertions.assertArrayEquals(expected,BMI_score_array);
        
    }
    private DietPlanner dietPlanner;
    @BeforeEach
    void setup(){
        this.dietPlanner = new DietPlanner(20,30,50);
    }
    
    @Test
    void not_static_class(){
    //given
        Coder coder1 = new Coder(1.82,75.0,26,Gender.MALE);
        Coder coder2 = new Coder(1.82,75.0,26,Gender.MALE);
        //DietPlan expected =new DietPlan(2202,110,73,275);
        
    //when
         DietPlan expected = dietPlanner.calculateDiet(coder1);
         DietPlan actual = dietPlanner.calculateDiet(coder2);


    //then
        Assertions.assertAll(
            ()-> Assertions.assertEquals(275,actual.getCarbohydrate()),
            ()-> Assertions.assertEquals(expected.getFat(),actual.getFat()),
            ()-> Assertions.assertEquals(expected.getProtein(),actual.getProtein()),
            ()-> Assertions.assertEquals(expected.getCarbohydrate(),actual.getCarbohydrate())
        );
        
    }
    @ParameterizedTest
    @ValueSource (doubles = {275,89.0,95.0,110.0} )
    public void parameterTest(Double coderWeight){
        //given
        double weight = coderWeight;
        double height = 1.72;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        Assertions.assertTrue(recommended);
    }
    //**************************************************************** */
    @ParameterizedTest (name ="height = {0},weight = {1}")
@CsvSource (value ={"87.0 , 1.77","81.22,1.70","70.0,1.660"})
    public void parameterTestWithCsvSource(Double coderWeight,Double coderHeight){
        //given
        double weight = coderWeight;
        double height = coderHeight;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        Assertions.assertTrue(recommended);
    }
//************************************************************************ */
    
@ParameterizedTest (name ="height = {0},weight = {1}")
@CsvFileSource (resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
    public void parameterTest(Double coderWeight,Double coderHeight){
        //given
        double weight = coderWeight;
        double height = coderHeight;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        Assertions.assertTrue(recommended);
    }
    @RepeatedTest (10)
    public void repeatedTest(){
        //given
        double weight = 88.1;
        double height = 1.80;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        Assertions.assertTrue(recommended);
    }
    @RepeatedTest (value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    public void should_return_true_when_the_diet_isRecommend(){
        //given
        double weight = 88.1;
        double height = 1.80;
        //when
        boolean recommended = BMICalculator.isDietRecommended(weight,height);
        //then
        Assertions.assertTrue(recommended);
    
    }
    @Test
    void performanceTimeTest (){
        //given
        List<Coder> coders = new ArrayList<>();
        for (int i=0;i<10000;i++){
            coders.add(new Coder (1.0+i,10.0+i));
        //when
        Executable executable =()->BMICalculator.findCoderWithWorstBMI(coders);
        //then
        Assertions.assertTimeout(Duration.ofMillis(200), executable);

        }
    }

}