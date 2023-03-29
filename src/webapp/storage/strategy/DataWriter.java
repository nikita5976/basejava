package webapp.storage.strategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

@FunctionalInterface
public interface DataWriter  {
  void writer (DataOutputStream dos, Collection<String> collection) throws IOException;
}
