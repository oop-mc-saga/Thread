package example2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * クライアントクラス
 * @author tadaki
 */
public class Client implements Runnable{

    private final int id;
    private final Server server;
    private volatile boolean running = true;
    private final Queue<Token> tokens;

    public Client(int id, Server server) {
        this.id = id;
        this.server = server;
        tokens = new LinkedList<>();
    }

    /**
     * 一回の動作
     */
    private void update(){
            if(!tokens.isEmpty()){//tokenがあればputする
                running=server.put(this, tokens.poll());
            }
            Token t = server.get(this);//サーバからtokenを取得
            if(t!=null){
                if(t==Server.falseToken)running=false;
                else{
                    tokens.add(t);
                }
            }        
    }
    @Override
    public void run() {
        while(running){
            update();
            int timeout = (int) (1000 * Math.random());
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public String toString(){
        return "client-" + id;
    }
}
