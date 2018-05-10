/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robosorter;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sbrandt
 */
public class EggSorter {
    public final Random RAND = new Random();
    public void sort_(Mover m,Board board) {
        board.move(0,2);
        board.carry(0,0);
        board.carry(0,1);
        board.carry(3,1);
        board.carry(2,1);
    }
    public void sort(Mover mv,Board board) {
        sort(mv,board,0,board.cells.length,0);
    }
    public void sort(Mover mv,Board board,int start,int end,int row) {
        for(int i=start;i<end;i++) {
            assert board.cells[i][2-row] != null;
            assert board.cells[i][row] == null;
        }
        int pivot = RAND.nextInt(board.cells.length);
        MartianEgg me = board.cells[pivot][2-row];
        int i1 = start, i2 = end;
        for(int i=0;i<board.cells.length;i++) {
            if(board.cells[i][2-row].num < me.num) {
                board.move(i,2-row);
                board.carry(i1++,row);
            } else if(board.cells[i][2-row].num > me.num) {
                board.move(i,2-row);
                board.carry(--i2,row);
            }
        }
        int imid = i1;
        for(int i=0;i<board.cells.length;i++) {
            if(board.cells[i][2-row] != null) {
                board.move(i,2-row);
                board.carry(imid++,row);
            }
        }
        System.out.println("Iteration finished");
//        for(int i=i1;i<i2;i++) {
//            board.move(i,2-row);
//            board.carry(i,row);
//        }
        assert(start <= i1);
        assert(i1 < i2);
        assert(i2 <= end);
        for(int i=0;i<board.cells.length;i++) {
            if(board.cells[i][0] != null) {
                for(int j=0;j<board.cells.length;j++) {
                    if(board.cells[j][2] == null) {
                        board.move(i,0);
                        board.carry(j, 2);
                        break;
                    }
                }
            }
        }
        System.out.println("Sort 1");
        sort(mv,board,start,i1,row);
        System.out.println("Sort 2");
        sort(mv,board,i2,end,row);
    }
}
