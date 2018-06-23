package com.funcy.g01.base.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ProbabilityGenerator {
	
	static final float[] normalDestribution =  new float[]{
		0.5f,0.504f,0.508f,0.512f,0.516f,0.5199f,0.5239f,0.5279f,0.5319f,0.5359f,
		0.5398f,0.5438f,0.5478f,0.5517f,0.5557f,0.5596f,0.5636f,0.5675f,0.5714f,0.5753f,
		0.5793f,0.5832f,0.5871f,0.591f,0.5948f,0.5987f,0.6026f,0.6064f,0.6103f,0.6141f,
		0.6179f,0.6217f,0.6255f,0.6293f,0.6331f,0.6368f,0.6404f,0.6443f,0.648f,0.6517f,
		0.6554f,0.6591f,0.6628f,0.6664f,0.67f,0.6736f,0.6772f,0.6808f,0.6844f,0.6879f,
		0.6915f,0.695f,0.6985f,0.7019f,0.7054f,0.7088f,0.7123f,0.7157f,0.719f,0.7224f,
		0.7257f,0.7291f,0.7324f,0.7357f,0.7389f,0.7422f,0.7454f,0.7486f,0.7517f,0.7549f,
		0.758f,0.7611f,0.7642f,0.7673f,0.7703f,0.7734f,0.7764f,0.7794f,0.7823f,0.7852f,
		0.7881f,0.791f,0.7939f,0.7967f,0.7995f,0.8023f,0.8051f,0.8078f,0.8106f,0.8133f,
		0.8159f,0.8186f,0.8212f,0.8238f,0.8264f,0.8289f,0.8355f,0.834f,0.8365f,0.8389f,
		0.8413f,0.8438f,0.8461f,0.8485f,0.8508f,0.8531f,0.8554f,0.8577f,0.8599f,0.8621f,
		0.8643f,0.8665f,0.8686f,0.8708f,0.8729f,0.8749f,0.877f,0.879f,0.881f,0.883f,
		0.8849f,0.8869f,0.8888f,0.8907f,0.8925f,0.8944f,0.8962f,0.898f,0.8997f,0.9015f,
		0.9032f,0.9049f,0.9066f,0.9082f,0.9099f,0.9115f,0.9131f,0.9147f,0.9162f,0.9177f,
		0.9192f,0.9207f,0.9222f,0.9236f,0.9251f,0.9265f,0.9279f,0.9292f,0.9306f,0.9319f,
		0.9332f,0.9345f,0.9357f,0.937f,0.9382f,0.9394f,0.9406f,0.9418f,0.943f,0.9441f,
		0.9452f,0.9463f,0.9474f,0.9484f,0.9495f,0.9505f,0.9515f,0.9525f,0.9535f,0.9535f,
		0.9554f,0.9564f,0.9573f,0.9582f,0.9591f,0.9599f,0.9608f,0.9616f,0.9625f,0.9633f,
		0.9641f,0.9648f,0.9656f,0.9664f,0.9672f,0.9678f,0.9686f,0.9693f,0.97f,0.9706f,
		0.9713f,0.9719f,0.9726f,0.9732f,0.9738f,0.9744f,0.975f,0.9756f,0.9762f,0.9767f,
		0.9772f,0.9778f,0.9783f,0.9788f,0.9793f,0.9798f,0.9803f,0.9808f,0.9812f,0.9817f,
		0.9821f,0.9826f,0.983f,0.9834f,0.9838f,0.9842f,0.9846f,0.985f,0.9854f,0.9857f,
		0.9861f,0.9864f,0.9868f,0.9871f,0.9874f,0.9878f,0.9881f,0.9884f,0.9887f,0.989f,
		0.9893f,0.9896f,0.9898f,0.9901f,0.9904f,0.9906f,0.9909f,0.9911f,0.9913f,0.9916f,
		0.9918f,0.992f,0.9922f,0.9925f,0.9927f,0.9929f,0.9931f,0.9932f,0.9934f,0.9936f,
		0.9938f,0.994f,0.9941f,0.9943f,0.9945f,0.9946f,0.9948f,0.9949f,0.9951f,0.9952f,
		0.9953f,0.9955f,0.9956f,0.9957f,0.9959f,0.996f,0.9961f,0.9962f,0.9963f,0.9964f,
		0.9965f,0.9966f,0.9967f,0.9968f,0.9969f,0.997f,0.9971f,0.9972f,0.9973f,0.9974f,
		0.9974f,0.9975f,0.9976f,0.9977f,0.9977f,0.9978f,0.9979f,0.9979f,0.998f,0.9981f,
		0.9981f,0.9982f,0.9982f,0.9983f,0.9984f,0.9984f,0.9985f,0.9985f,0.9986f,0.9986f
	};

	
	float getXofND(int index){
		return 0.01f * index;
	}
	
	/**
	 * FIXME: to be changed to binary search
	 * @return
	 */
	public float getStandardNormalRandomNumber(){
		float rand = getR().nextFloat();
		int sign = 1;
		if(rand < 0.5 && rand > 0){
			rand = 1 - rand;
			sign = -1;
		}
		//default:
		int found = normalDestribution.length;
		int i = 0;
		for (; i < normalDestribution.length; i++) {
			float value = normalDestribution[i];
			if (rand < value){
				found = i;
				break;
			}
		}
		return sign * getXofND(found);
	}
	
	public ProbabilityGenerator() {
		this(-1L);
	}
	public ProbabilityGenerator(long seed) {
		this.seed = seed;
	}
	
	private static ThreadLocal<Random> randoms = new ThreadLocal<Random>();
	private static long seed;
	
	private static Random getR(){
	    Random random = randoms.get();
	    if(random == null){
	    	random = new Random();
	        randoms.set(random);
	    }
        return random;
	}
	
	public void setR(Random r) {
	    randoms.set(r);
	}

	/**
         * 产生min到max之间的整数
         * @param min
         * @param max
         * @return
         */
	public static int getRandomNumber(int min, int max) {
		int result = getR().nextInt((max - min + 1) << 5);
		return (result >> 5) + min;
	}
	
	public float getRandomNum(float min, float max) {
		float result = getR().nextFloat() * (max - min) + min ;
		return result;
	}
	
	/**
     * 从权重数组中，随机number个index，可重复
     * @param ratios
     * @param number
     * @return
     */
	public static int[] getMutilRandomChoiceWithRatioArrCanRepeat(int[] ratios, int number) {
		int[] randomIndexArray = new int[number];
		
		//copy权重数组，防止修改
		int[] tempRatioArray = new int[ratios.length];
		
		System.arraycopy(ratios, 0, tempRatioArray, 0, ratios.length);
		
		for (int i = 0; i < number; i++) {
			int ratioIndex = getRandomChoiceWithRatioArr(tempRatioArray);
			randomIndexArray[i] = ratioIndex;
		}
		
		return randomIndexArray;
	} 
	
	/**
         * 产生概率区间（总概率必须为100%）
         * @param percentArr 概率数组，和为100
         * @return
         */
	public int getRandomChoiceWithPercentArr(int[] percentArr) {
		int sum = 0;
		for (int i : percentArr) {
			sum +=i;
		}
		if (sum != 100) throw new RuntimeException("Sum(percentArr,i) must be 100.");
		
		int r = getRandomNumber(99);
		int total = 0;
		for (int i = 0; i < percentArr.length; i++) {
			total += percentArr[i];
			if (r < total ){
				return i;
			}
		}
		throw new RuntimeException("It can't be here!");
	}
	
	/**
         * 产生概率区间
         * @param ratios 概率权重数组，和可以不为100；但产生的概率的和为100
         * @return
         */
	public static int getRandomChoiceWithRatioArr(int[] ratios) {
		int sum = 0;
		for (int i : ratios) {
			sum += i;
		}
		
		int r = getRandomNumber(1,sum);
		int total = 0;
		for (int i = 0; i < ratios.length; i++) {
			total += ratios[i];
			if (r <= total ){
				return i;
			}
		}
		throw new RuntimeException("It can't be here!");
	}
	
	/**
         * 从权重数组中，随机number个index
         * @param ratios
         * @param number
         * @return
         */
	public static int[] getMutilRandomChoiceWithRatioArr(int[] ratios, int number) {
		
		if (ratios.length < number) {
			throw new RuntimeException("bad params");
		}
		
		int[] randomIndexArray = new int[number];
		
		//copy权重数组，防止修改
		int[] tempRatioArray = new int[ratios.length];
		
		System.arraycopy(ratios, 0, tempRatioArray, 0, ratios.length);
		
		for (int i = 0; i < number; i++) {
		
			int ratioIndex = getRandomChoiceWithRatioArr(tempRatioArray);
			
			randomIndexArray[i] = ratioIndex;
			
			tempRatioArray[ratioIndex] = 0;
		}
		
		return randomIndexArray;
	}
	
	/**
     * 从权重数组中，随机number个index，number可以大于ratios[]长度
     * @param ratios
     * @param number
     * @return
     */
	public static int[] getMutilRandomChoiceWithRatioArrWithLargerNumber(int[] ratios, int number) {
		
		int[] randomIndexArray = new int[number];
		
		//copy权重数组，防止修改
		int[] tempRatioArray = new int[ratios.length];
		
		System.arraycopy(ratios, 0, tempRatioArray, 0, ratios.length);
		
		for (int i = 0; i < number; i++) {
		
			int ratioIndex = getRandomChoiceWithRatioArr(tempRatioArray);
			
			randomIndexArray[i] = ratioIndex;
			
		}
		
		return randomIndexArray;
	}

	/**
         * 产生概率区间
         * @param ratios 概率权重数组，和可以不为100；但产生的概率的和为100
         * @return
         */
	public static int getRandomChoiceWithRatioArr(float[] ratios) {
		float sum = 0;
		for (float i : ratios) {
			sum += i;
		}
		
		float f = getR().nextFloat();
		
		float total = 0;
		for (int i = 0; i < ratios.length; i++) {
			total += ratios[i] / sum;
			if (f <= total ){
				return i;
			}
		}
		throw new RuntimeException("It can't be here!");
	}
	
	/**
         * 依据百分比返回真
         * @param percentage
         * @return true in random of percentage
         */
	public static boolean getRandomWithPercentage(float percentage) {
		float nextFloat = getR().nextFloat();
		return nextFloat < percentage;
	}
	
	public static boolean getRandomBoolean() {
		return getR().nextBoolean();
	}

	public int getRandomChoiceWithPermillageArr(int[] permililageArr) {
		int sum = 0;
		for (int i : permililageArr) {
			sum +=i;
		}
		if (sum != 1000) throw new RuntimeException("Sum(percentArr,i) must be 1000.");
		
		int r = getRandomNumber(999);
		int total = 0;
		for (int i = 0; i < permililageArr.length; i++) {
			total += permililageArr[i];
			if (r < total ){
				return i;
			}
		}
		throw new RuntimeException("It can't be here!");
	}

	public float nextFloat() {
		return getR().nextFloat();
	}

	/**
         * 一次返回0到max之间（不包括max）之间的多个数。
         * @param size
         * @param count 多个数
         * @return
         */
	public int[] getRandomNumbers(int max, int count) {
		int[] num = new int[count];
		for (int i = 0; i < count; i++) {
			num[i] = getRandomNumber(max);
		}
		return num;
	}
	
	/**
         * 不重复
         * @param max
         * @param count
         * @return
         */
	public static int[] getRandomNumbersWithoutRepeat(int max, int count){
		if (count > max + 1){
			throw new RuntimeException("bad params, too less choice.");
		}
		Set<Integer> numSet = new HashSet<Integer>();
		while(numSet.size() < count) {
			numSet.add(getRandomNumber(max));
		}
		
		int[] nums = new int[count];
		int i = 0;
		for (Integer num : numSet) {
			nums[i] = num;
			i++;
		}
		return nums;
	}
	
	/**
     * 产生0到max之间的整数，包括max，概率平均
     * 例如max=3，则产生0,1,2,3这四个数字的概率均为25%
     * [min, max]
     * @param max
     * @return
     */
	public static int getRandomNumber(int max) {
		return getRandomNumber(0,max);
	}
	
	
    /**
     * 数组中有可能为0的 需要忽略掉
     * [80,60,50,20]
     */
    public int getAverageChoiceIndex(float[] percentageArr) {
        float choice= this.nextFloat();
        float occurChoice = 1;
        float totalOccurChoice = 0;
        for (float percentage : percentageArr) {
            occurChoice *= (1- percentage / 100);
            totalOccurChoice += percentage;
        }
        occurChoice = 1 - occurChoice;
        
        if(choice < occurChoice) {
            int index = 0;
            float currentIndexChoice = 0;
            for (float percentage : percentageArr) {
                currentIndexChoice += percentage / totalOccurChoice * occurChoice;
                if(choice < currentIndexChoice){
                    return index;
                }
                index ++;
            }
        }
        return -1;
    }

}
