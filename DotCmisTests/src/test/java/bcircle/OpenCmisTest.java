package bcircle;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.ChangeType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OpenCmisTest {
    public Session createSession() {
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.put(SessionParameter.USER, "admin");
        parameter.put(SessionParameter.PASSWORD, "admin");

        parameter.put(SessionParameter.ATOMPUB_URL, "http://192.168.0.20:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, "-default-");

        Session session = factory.createSession(parameter);
        return session;
    }

    @Test
    public void checkLogTest() {
        Session session = createSession();

        RepositoryInfo ri = session.getBinding().getRepositoryService().getRepositoryInfo(session.getRepositoryInfo().getId(), null);
        String latestChangeLogToken = ri.getLatestChangeLogToken();
        System.out.println(latestChangeLogToken);


        String token = "0";

        while (token != null) {
            ChangeEvents changes = session.getContentChanges(token, false, 1);
            for (ChangeEvent change : changes.getChangeEvents()) {
                System.out.println(change.getChangeType());
                if (change.getChangeType() != ChangeType.DELETED) {
                    String id = change.getObjectId();
                    CmisObject object = session.getObject(id);
                    System.out.println(object.getName());
                }
            }
            token = changes.getLatestChangeLogToken();
            System.out.println("Token = " + token);
        }
    }
}
