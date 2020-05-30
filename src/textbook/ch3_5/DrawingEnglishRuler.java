package textbook.ch3_5;

public class DrawingEnglishRuler {

    public static void main(String[] args){
        drawRuler(1,5);
    }

    /**
     * Draw an english ruler
     * @param nInches 尺的刻度要畫到多長(英吋)
     * @param majorLength 最大刻度要多少
     */
    public static void drawRuler(int nInches, int majorLength) {
        drawOneTick(majorLength, 0);
        for(int i = 1; i <= nInches; i++) {
            drawTicks(majorLength - 1);
            drawOneTick(majorLength, i);
        }
    }

    //draw a tick wih no label
    public static void drawOneTick(int tickLength) {
        drawOneTick(tickLength, -1);
    }

    //draw a tick with label
    public static void drawOneTick(int tikLength, int label) {
        //draw ticks
        for(int i = 0; i < tikLength; i++){
            System.out.print("-");
        }
        //draw label
        if(label >= 0) {
            System.out.println(" " + label);
        }else{
            System.out.println();
        }
    }

    public static void drawTicks(int tickLength) {
        if(tickLength > 0) {
            drawTicks(tickLength - 1);
            drawOneTick(tickLength);
            drawTicks(tickLength - 1);
        }
    }
}
