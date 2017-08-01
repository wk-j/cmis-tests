package bcircle;

import org.apache.chemistry.opencmis.client.api.ChangeEvent;
import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OpenCmisTest {
    public  Session createSession () {
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.put(SessionParameter.USER, "admin");
        parameter.put(SessionParameter.PASSWORD, "admin");

        parameter.put(SessionParameter.ATOMPUB_URL, "http://192.168.0.20:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, "-default-");

        Session session = factory.createSession(parameter);
        return  session;
    }

    @Test
    public  void checkLogTest() {
       Session session = createSession();
       ChangeEvents changes = session.getContentChanges(null, false, 100);
       for(ChangeEvent change : changes.getChangeEvents()) {
           System.out.println(change.getChangeType());
           System.out.println(change.getObjectId());
       }
        System.out.println(changes.getLatestChangeLogToken());
    }
}
