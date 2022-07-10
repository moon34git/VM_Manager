import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to emulate the physical memory
 */
public class PhysicalMemory{
    /**
     * variable to emulate frames in memory
     */
    Frame[] frames;
    /**
     * we need a variable to store how many
     * frames are used
     */
    int currentFreeFrame;
    LinkedList<Integer> fifoList = new LinkedList<>();
    LinkedList<Integer> lruList = new LinkedList<>();


    /**
     * Constructor
     */
    public PhysicalMemory(){
        this.frames = new Frame[128];
        this.currentFreeFrame = 0;
    }


    /**
     * function to add a new frame to memory
     *
     * @param f Frame to be added
     * @return int the frame number just added
     */
    public int addFrame(Frame f){
        this.frames[this.currentFreeFrame] = new Frame(f.data);
        this.currentFreeFrame++;
        return this.currentFreeFrame - 1;
    }


    /**
     * function to get value in memory
     *
     * @param f_num int frame number
     * @param offset int offset
     * @return int content in specified location
     */
    public int getValue(int f_num, int offset){
        Frame frame = this.frames[f_num];
        return frame.data[offset];
    }

    public int FIFO(int p_num) {
        if(fifoList.size() >= 128) {
            int firstIn = fifoList.poll();      //Since no room for a new page number, to store it, remove the oldest one
            fifoList.offer(p_num);              //Add Page number
            return firstIn;                     //Return the oldest page number
        }else {
            fifoList.offer(p_num);              //Add Page number
            return 0;
        }
    }

    int first = 0;
    public int LRU(int p_num) {
        if(lruList.contains(p_num)) {
            lruList.remove((Integer) p_num);    //If the page number already exists, it is deleted.
        }else {
            if(lruList.size() >= 128) {
                first = lruList.getFirst();     //Since no room for a new page number, to store it,
                lruList.poll();                 //remove the LRU value.
            }
        }
        lruList.add(p_num);                     //Updated to recent value
        return first;                           //Return removed page number or 0
    }


}


/**
 * wrapper class to group all frame related logics
 */
class Frame {
    /**
     * variable to store datas of this frame
     */
    int[] data;


    /**
     * Constructor
     *
     * @param d int[256] for initializing frame
     */
    public Frame(int[] d){
        this.data = new int[256];
        for(int i=0;i<256;i++){
            this.data[i] = d[i];
        }
    }


    /**
     * Copy Constructor
     *
     * @param f Frame to be copied
     */
    public Frame(Frame f){
        this.data = new int[256];
        System.arraycopy(f.data, 0, this.data, 0, 256);
    }
}

