package PreProcessData;
import java.io.*;
import java.util.*;

public interface recipeCollection {

	public abstract Map<String, String> nextrecipe() throws IOException;
}
