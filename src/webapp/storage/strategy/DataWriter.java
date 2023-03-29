package webapp.storage.strategy;

import java.io.DataOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface DataWriter  {
  void writer (DataOutputStream dos, String element) throws IOException;
}
