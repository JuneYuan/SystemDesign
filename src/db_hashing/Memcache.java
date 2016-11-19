package db_hashing;

import java.util.HashMap;

public class Memcache {

    HashMap<Integer, Wrapper> map;
    
    private class Wrapper {
        int value;
        int lb, ub;  // lower bound and upper bound of valid time
        
        public Wrapper(int value, int lb, int ub) {
            this.value = value;
            this.lb = lb;
            this.ub = ub;
        }
        
        public void incr(int delta) {
            this.value += delta;
        }
        
        public void decr(int delta) {
            this.value -= delta;
        }
    }
    
    public Memcache() {
        // Initialize your data structure here.
        map = new HashMap<>();
    }

    public int get(int currTime, int key) {
        // Write your code here
        Wrapper wrapper = map.get(key);
        if (wrapper == null || wrapper.lb > currTime || wrapper.ub <= currTime)
            return 2147483647;
        return wrapper.value;
    }

    public void set(int currTime, int key, int value, int ttl) {
        // Write your code here
        int ub = (ttl == 0) ? Integer.MAX_VALUE : currTime + ttl;
        map.put(key, new Wrapper(value, currTime, ub));
    }

    public void delete(int curtTime, int key) {
        // Write your code here
        map.remove(key);
    }
    
    public int incr(int currTime, int key, int delta) {
        // Write your code here
        Wrapper wrapper = map.get(key);
        if (wrapper == null || wrapper.lb > currTime || wrapper.ub <= currTime)
            return 2147483647;
        
        wrapper.incr(delta);
        return wrapper.value;
    }

    public int decr(int currTime, int key, int delta) {
        // Write your code here
        Wrapper wrapper = map.get(key);
        if (wrapper == null || wrapper.lb > currTime || wrapper.ub <= currTime)
            return 2147483647;
        wrapper.decr(delta);
        return wrapper.value;
    }

}
