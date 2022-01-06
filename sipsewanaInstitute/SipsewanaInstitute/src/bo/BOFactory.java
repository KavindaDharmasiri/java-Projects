package bo;

import bo.custom.impl.CreateAccountBoImpl;
import bo.custom.impl.DetailBoImpl;
import bo.custom.impl.ProgramBoImpl;
import bo.custom.impl.StudentBoImpl;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 4:18 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case DETAIL:
                return new DetailBoImpl();
            case PROGRAM:
                return new ProgramBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            case CREATE_ACCOUNT:
                return new CreateAccountBoImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        DETAIL, PROGRAM, STUDENT, CREATE_ACCOUNT
    }
}
