package webapp.storage.strategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

public class AnyDataWrite  {
    public void anyWriter(DataOutputStream dos, Collection<String> collection) throws IOException {
        for (String element : collection) {
            dos.writeUTF(element);
        }
    }
}

