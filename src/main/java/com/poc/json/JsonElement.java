package com.poc.json;
  

/**
 * A class representing an element of Json. It could either be a {@link JsonObject}, a
 * {@link JsonArray}, a {@link JsonPrimitive} or a {@link JsonNull}.
 *
 * @author Bharavi Gade
 */
public class JsonElement {
 
   public JsonElement( ) {
	 
   }
  /**
   * provides check for verifying if this element is a Json object or not.
   *
   * @return true if this element is of type {@link JsonObject}, false otherwise.
   */
  public boolean isJsonObject() {
    return this instanceof JsonObject;
  }

 

  /**
   * convenience method to get this element as a {@link JsonObject}. If the element is of some
   * other type, a {@link IllegalStateException} will result. Hence it is best to use this method
   * after ensuring that this element is of the desired type by calling {@link #isJsonObject()}
   * first.
   *
   * @return get this element as a {@link JsonObject}.
   * @throws IllegalStateException if the element is of another type.
   */
  public JsonObject getAsJsonObject() {
    if (isJsonObject()) {
      return (JsonObject) this;
    }
    throw new IllegalStateException("Not a JSON Object: " + this);
  }

  
}
   

   
 