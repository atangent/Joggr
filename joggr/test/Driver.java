import java.util.List;
import java.io.IOException;

/**
 * TEST CODE tests for functions of constructor, matchRunners, readRunners, setDistance, findMatches, displayMatches, and IllegalClubMember inputs (age, time, distance)
 *
 * @author Amy Tang 20130856
 * @version 1.0
 * July 29, 2019
 */
public class Driver {
    public static void main(String[] args) throws IllegalClubMember, IOException {
            //create joggrs
            ClubMember joggr1 = new ClubMember("Darwin", 30, 10, true, "morning");  //match with joggr2
            ClubMember joggr2 = new ClubMember("Einstein", 33, 10, true, "morning");  //match with joggr1
            ClubMember joggr3 = new ClubMember("DaVinci", 46, 30, false, "evening");  //not match

            //test for matchRunners
            System.out.println("Test matchRunners... ");
            System.out.println("Do " + joggr1.getName() + " and " + joggr2.getName() + " match? " + joggr1.matchRunners(joggr2));  //true
            System.out.println("Do " + joggr1.getName() + " and " + joggr3.getName() + " match? " + joggr1.matchRunners(joggr3));  //false
            System.out.println("\n");

            //test for readRunners
            System.out.println("Testing readRunners... ");
            List<ClubMember> joggrList = ClubMember.readRunners();
            for (ClubMember joggr : joggrList) {
            System.out.println(joggr);  //all runners in text file
            }
            System.out.println("\n");

            //test for setDistance
            System.out.println("Testing setDistance... ");
            System.out.println(joggr3);  //30km
            joggr3.setDistance(5);
            System.out.println("After setDistance(5) ");
            System.out.println(joggr3);  //5km
            System.out.println("\n");

            //testing findMatches
            System.out.println("Testing findMatches... ");
            System.out.println(joggr3.getName() + "'s matches: " + joggr3.findMatches(joggr3));
            System.out.println("\n");

            //testing displayPairs
            System.out.println("Testing displayPairs... ");
            ClubMember.displayPairs(joggrList);
            System.out.println("\n");

            //Testing illegal inputs

            //AGE
            //ClubMember ageException = new ClubMember("ageException", 19, 5, true, "morning");

            //DIST_KM
            //ClubMember distException = new ClubMember("distException", 20, 7, true, "morning");

            //TIME_DAY
            //ClubMember timeException = new ClubMember("timeException", 70, 5, true, "lunch");

        }  //end method

    }  //end program