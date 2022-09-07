 

package com.poc.json; 

import java.util.ArrayList;
import java.util.List; 

/**
 * A class representing an object type in Json. An object consists of name-value pairs where names
 * are strings, and values are any other type of {@link JsonElement}. This allows for a creating a
 * tree of JsonElements. The member elements of this object are maintained in order they were added.
 *
 * @author Bharavi
 */
public final class JsonObject extends JsonElement {
 
	//private final List<String, JsonElement> members = new ArrayList<String, JsonElement>();
	private final List<Entry<String,JsonElement>> members = new ArrayList<Entry<String,JsonElement>>();
	 

  /**
   * Adds a member, which is a name-value pair, to self. The name must be a String, but the value
   * can be an arbitrary JsonElement, thereby allowing you to build a full tree of JsonElements
   * rooted at this node.
   *
   * @param property name of the member.
   * @param value the member object.
   */
  public void add(String property, JsonElement value) {
     members.add(new Entry<String,JsonElement>(property, value));
  }
 
  /**
   * Convenience method to add a primitive member. The specified value is converted to a
   * JsonPrimitive of String.
   *
   * @param property name of the member.
   * @param value the string value associated with the member.
   */
  public void addProperty(String property, String name) {
    add(property, new JsonElement());
  }
 
  /**
   * Returns a set of members of this object. The set is ordered, and the order is in which the
   * elements were added.
   *
   * @return a set of members of this object.
   */
  public List<Entry<String,JsonElement>> entrySet() {
    return members; 
  }
  
  /**
   * Returns the number of key/value pairs in the object.
   *
   * @return the number of key/value pairs in the object.
   */
  public int size() {
    return members.size();
  }

  /**
   * Convenience method to check if a member with the specified name is present in this object.
   *
   * @param memberName name of the member that is being checked for presence.
   * @return true if there is a member with the specified name, false otherwise.
   */
  /*
  public boolean has(String memberName) {
    return members.containsKey(memberName);
  }
*/
  /**
   * Returns the member with the specified name.
   *
   * @param memberName name of the member that is being requested.
   * @return the member matching the name. Null if no such member exists.
   */
 // public JsonElement get(String memberName) {
  //  return members.get(memberName);
  //}
 

  /**
   * Convenience method to get the specified member as a JsonObject.
   *
   * @param memberName name of the member being requested.
   * @return the JsonObject corresponding to the specified member.
   */
  //public JsonObject getAsJsonObject(String memberName) {
  //  return (JsonObject) members.get(memberName);
  //}
 
  class Entry<K,V> {
	  K key;
	  V value;
	  public Entry(K key,V value) {
	 	 this.key = key;
	 	 this.value = value;
	  }
	 public K getKey() {
	 	return key;
	 }
	  
	 public V getValue() {
	 	return value;
	 }
	  
	  
	 }
   
}
