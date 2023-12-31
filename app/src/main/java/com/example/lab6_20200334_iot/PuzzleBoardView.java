package com.example.lab6_20200334_iot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;

    //public static boolean GANASTE = PuzzleActivity.GANASTE;
    public int Score;

    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
        Score = -1;
    }

    public void initialize(Bitmap imageBitmap) {

        this.invalidate();
        int width = getMeasuredWidth();
        Log.d("TAG","PuzzleBoard Object Called with width "+ width);
        puzzleBoard = new PuzzleBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("TAG","onDraw");
        super.onDraw(canvas);

        PuzzleActivity.setScore(Score);

        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                    activity.finish();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        /*if (animation == null && puzzleBoard != null) {
            // Do something. Then:
            Score = 0;
            Log.d("TAG","Need to do something in order to shiffle");
            for(int i=0; i< 25;i++){
                ArrayList<PuzzleBoard> neighbours = puzzleBoard.neighbours(); //get possible board form this current board
                int randIndex = random.nextInt( neighbours.size() );
                //may be useful in wrost case
                while ( neighbours.get(randIndex).resolved() == true ){
                    randIndex = (randIndex+1) % (neighbours.size() );
                }
                puzzleBoard = neighbours.get(randIndex);   //select random board
            }
            invalidate();
        }
        else{
            Toast.makeText(getContext(),"Take a picture and then try shuffle",Toast.LENGTH_SHORT).show();
        }*/  // REORDENA POR PARTES (SOLO CIERTO LUGAR DE LA IMAGEN)

        //ESTE CODIGO REALIZA ITERACIOONES DE REORDENAMIENTO ANTES DE MOSTRAR LA IMAGEN TOTALMENTE DESORDENADA.
       /* if (animation == null && puzzleBoard != null) {
            Score = 0;
            Log.d("TAG", "Shuffling completely...");

            // Obtén una lista de posibles movimientos aleatorios
            ArrayList<PuzzleBoard> randomMoves = new ArrayList<>();

            for (int i = 0; i < 100; i++) { // Realiza 100 movimientos aleatorios (puedes ajustar este número)
                ArrayList<PuzzleBoard> neighbours = puzzleBoard.neighbours();
                int randIndex = random.nextInt(neighbours.size());

                // Agrega el vecino en el índice aleatorio a la lista de movimientos aleatorios
                randomMoves.add(neighbours.get(randIndex));

                // Actualiza puzzleBoard para que sea igual al vecino seleccionado
                puzzleBoard = neighbours.get(randIndex);
            }

            // Realiza un reordenamiento completo aleatorio
            for (PuzzleBoard board : randomMoves) {
                puzzleBoard = board;
                invalidate();
            }
        } else {
            Toast.makeText(getContext(), "Take a picture and then try shuffle", Toast.LENGTH_SHORT).show();
        }*/


        //CODIGO DE DESORDENAMIENTO VARIANTE
        /*if (animation == null && puzzleBoard != null) {
            Score = 0;
            Log.d("TAG", "Shuffling completely...");

            // Obtén una lista de posibles movimientos aleatorios
            ArrayList<PuzzleBoard> randomMoves = new ArrayList<>();

            // Realiza movimientos aleatorios
            for (int i = puzzleBoard.getBoardSize() * puzzleBoard.getBoardSize() * puzzleBoard.getBoardSize(); i > 0; i--) {
                ArrayList<PuzzleBoard> neighbours = puzzleBoard.neighbours();
                int randIndex = random.nextInt(neighbours.size());

                // Agrega el vecino en el índice aleatorio a la lista de movimientos aleatorios
                randomMoves.add(neighbours.get(randIndex));

                // Actualiza puzzleBoard para que sea igual al vecino seleccionado
                puzzleBoard = neighbours.get(randIndex);
            }

            // Realiza un reordenamiento completo aleatorio
            for (PuzzleBoard board : randomMoves) {
                puzzleBoard = board;
                invalidate();
            }
        } else {
            Toast.makeText(getContext(), "Take a picture and then try shuffle", Toast.LENGTH_SHORT).show();
        }*/

        //UTILIZANDO EL ALGORITMO DE FISHER-YATES
        if (animation == null && puzzleBoard != null) {
            Score = 0;
            Log.d("TAG", "Shuffling completely...");

            int boardSize = puzzleBoard.getBoardSize();

            // Realiza movimientos aleatorios en el mismo tablero
            for (int i = boardSize - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int tileIndexA = i;
                int tileIndexB = j;

                // Intercambia las dos piezas aleatoriamente
                puzzleBoard.swapTiles(tileIndexA, tileIndexB);

                // Actualiza la vista
                invalidate();
            }
        } else {
            Toast.makeText(getContext(), "Take a picture and then try shuffle", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if(Score != -1){
                            Score++;
                        }
                        if (puzzleBoard.resolved()) {
                            PuzzleActivity.setBestScore(Score);
                            String msg = "Felicidades, Ganaste! \n Movimientos : "+Score;
                            if (Score == -1) {
                                msg = "Primero Comienza el JUEGO, luego resuelve. Vuelve a intentar";
                            }
                            Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_LONG);
                            toast.show();
                            Score = -1;
                            activity.finish();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }

    public void modifyAttributeInActivity(boolean newValue) {
        PuzzleActivity puzzleActivity = (PuzzleActivity) activity;
        puzzleActivity.modifyAttributeFromMyClass(newValue);
    }

    private Comparator<PuzzleBoard> comparator = new Comparator<PuzzleBoard>() {
        @Override
        public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
            return lhs.priority() - rhs.priority();
        }
    };



    public void solve() {
        if( animation == null && puzzleBoard != null) {

            Score = -1;

            if( puzzleBoard.resolved() ){
                Toast.makeText(getContext(),"Already Solved!",Toast.LENGTH_SHORT).show();
                return ;
            }

            Log.d("TAG", "solve: start");

            int z = 0;
            PriorityQueue<PuzzleBoard> boardQueue = new PriorityQueue<>(1, comparator);

            PuzzleBoard current = new PuzzleBoard(puzzleBoard);
            current.setPreviousBoard(null);
            current.setStep(0);
            boardQueue.add(current);
            HashSet<String> set = new HashSet<>();

            //Time Testing
            long startTime = System.currentTimeMillis();

            while (!(boardQueue.isEmpty())) {

                Log.d("TAG", "Step : " + z);
                z++;
                PuzzleBoard bestState = boardQueue.poll();

                set.remove(bestState.convertToString());

                if (bestState.resolved()) {

                    ArrayList<PuzzleBoard> solution = new ArrayList<>();
                    while (bestState.getPreviousBoard() != null) {
                        solution.add(bestState);
                        bestState = bestState.getPreviousBoard();
                    }
                    Collections.reverse(solution);
                    boardQueue.clear();
                    animation = solution;

                    long endTime = System.currentTimeMillis();
                    long timeTaken = endTime-startTime;

                    Toast.makeText(getContext(),"Time : "+timeTaken+"ms",Toast.LENGTH_LONG).show();

                    invalidate(); //update UI
                    break;
                }
                else{
                    for (PuzzleBoard tempBoard : bestState.neighbours()) {
                        String s = tempBoard.convertToString();
                        if (!(set.contains(s))) {
                            set.add(s);
                            boardQueue.add(tempBoard);
                        }
                    }

                    long endTime = System.currentTimeMillis();
                    long timeTaken = endTime-startTime;
                    if(timeTaken > 10000){
                        boardQueue.clear();
                        Toast.makeText(getContext(),"Taking too much time...",Toast.LENGTH_LONG).show();
                        invalidate();
                        break;
                    }
                }
            }
        }
        else if (puzzleBoard == null) {
            Toast.makeText(getContext(),"Take a picture and shuffle it",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(),"Shuffle it and then click on solve",Toast.LENGTH_SHORT).show();
        }
    }
}
