package com.lyt;



public class findSaddlePoints {
    public static void main(String[] args) {
        int [] [] num={{1,2,3},{4,5,6},{7,8,9}};
        findSaddlePoints( num );
        int [] arr={1,90,8,4,5,};
       // System.out.println(findMax(arr));
    }

    static int findSaddlePoints(int[][] num) {

        int max=0;
        int min ;

        int colIndex=0;
        int rowIndex=0;
        //先按行遍历
        for(int i=0;i<num.length;i++) {

            //for循环遍历输出数组
            for(int j=0;j<num[i].length;j++) {
              //  System.out.println(num[i][j]+" ");  //先找到行最大 然后根据当前的列索引来找到该列的最小项
                if(num[i][j]>max){
                    max=num[i][j];
                    colIndex=j;
                    rowIndex=i;
                }

//                if(num[j][i]<min){
//                    min=num[j][i];
//                }
                
            }


           // System.out.println("所在列 "  +colIndex);
            //再遍历所在的列

                ;   //  根据 row的 列索引 去遍历所在列 找到行最小 然后存起来
                    min=num[0][colIndex];
                if (num[i][colIndex] < min) {
                    min = num[i][colIndex];
                }


            System.out.println("所在列  "+num[i][colIndex]);
            System.out.println("min "+min);
            System.out.println("max "+max);
            if(max==min){
                System.out.println("找到了鞍点"+"坐标是 ："+"num["+ i+"]"+"["+colIndex+"]" );
            }






        }
        return 0;
    }

    static int findMax(int[] num) {
        int j=0;
        int max=0;
        //先按行遍历
        for(int i=0;i<num.length;i++){
            //for循环遍历输出数组
            if(num[i]>max){
                max=num[i];
            }
        }



        return max;
    }


}
