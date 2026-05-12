public class Company {
    int n;
     boolean f = false;
    synchronized public void produce_item(){
        if(f){
            try{wait();}catch(Exception e){System.out.println(e);}
        }
        this.n = n;
        System.out.println("Produced item: " + this.n);
        f = true;
        notify();

    }
    synchronized public int consume_item(){
        if(!f){
            try{wait();}catch(Exception e){System.out.println(e);}
        }
        System.out.println("Consumed item: " + this.n);
        f = false;
        notify();
        return this.n;
    }
}
