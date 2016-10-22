package intro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MiniTwitter {
    private Map<Integer, Set<Integer>> friends;     // Key: fromId, Value: toIds
    private Map<Integer, List<Node>> usersTweets;   // Key: ?, Value: tweet Node
    private int order;  // ???
    
    private class Node {
        int order;
        Tweet tweet;
        public Node(int order, Tweet tweet) {
            this.order = order;
            this.tweet = tweet;
        }
    }

    public MiniTwitter() {
        // initialize your data structure here.
        this.friends = new HashMap<Integer, Set<Integer>>();
        this.usersTweets = new HashMap<Integer, List<Node>>();
        this.order = 0;
    }

    // @param user_id an integer
    // @param tweet a string
    // return a tweet
    public Tweet postTweet(int userId, String tweetText) {
        //  Write your code here
        Tweet tweet = Tweet.create(userId, tweetText);
        if (!usersTweets.containsKey(userId)) {
            usersTweets.put(userId, new ArrayList<Node>());
        }
        order += 1;
        usersTweets.get(userId).add(new Node(order, tweet));
        
        return tweet;
    }

    // @param user_id an integer
    // return a list of 10 new feeds recently
    // and sort by timeline
    public List<Tweet> getNewsFeed(int userId) {
        // Write your code here
        List<Tweet> result = new ArrayList<>();
        
        List<Node> tmp = new ArrayList<>();
        if (usersTweets.containsKey(userId)) {
            tmp.addAll(getLastTen(usersTweets.get(userId)));
        }
        if (friends.containsKey(userId)) {
            for (Integer user : friends.get(userId)) {
                if (usersTweets.containsKey(user)) {
                    tmp.addAll(getLastTen(usersTweets.get(user)));
                }
            }
        }
        
        Collections.sort(tmp, new ByOrder());
        tmp = getFirstTen(tmp);
        for (Node node : tmp) {
            result.add(node.tweet);
        }
        
        return result;
    }
        
    // @param user_id an integer
    // return a list of 10 new posts recently
    // and sort by timeline
    public List<Tweet>  getTimeline(int userId) {
        // Write your code here
        List<Tweet> result = new ArrayList<>();
        
        List<Node> tmp = new ArrayList<>();
        if (usersTweets.containsKey(userId)) {
            tmp.addAll(getLastTen(usersTweets.get(userId)));
        }
        
        Collections.sort(tmp, new ByOrder());
        tmp = getFirstTen(tmp);
        for (Node node : tmp) {
            result.add(node.tweet);
        }
        
        return result;
    }
    
    private List<Node> getLastTen(List<Node> tmp) {
        int last = 10;
        if (tmp.size() < 10) {
            last = tmp.size();
        }
        
        return tmp.subList(tmp.size() - last, tmp.size());
    }
    
    private List<Node> getFirstTen(List<Node> tmp) {
        int last = 10;
        if (tmp.size() < 10)
            last = tmp.size();
            
        return tmp.subList(0, last);        
    }
    
    private class ByOrder implements Comparator {
        public int compare(Object obj1, Object obj2) {
            Node node1 = (Node) obj1;
            Node node2 = (Node) obj2;
            if (node1.order < node2.order)  return 1;
            if (node1.order > node2.order)  return -1;
            return 0;
        }
    }

    // @param from_user_id an integer
    // @param to_user_id an integer
    // from user_id follows to_user_id
    public void follow(int fromUserId, int toUserId) {
        // Write your code here
        if (!friends.containsKey(fromUserId)) {
            friends.put(fromUserId, new HashSet<Integer>());
        }
        
        friends.get(fromUserId).add(toUserId);
    }

    // @param from_user_id an integer
    // @param to_user_id an integer
    // from user_id unfollows to_user_id
    public void unfollow(int fromUserId, int toUserId) {
        // Write your code here
        if (friends.containsKey(fromUserId)) {
            friends.get(fromUserId).remove(toUserId);
        }
    }
}