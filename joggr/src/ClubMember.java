import java.io.*;
import java.util.*;
import java.io.IOException;

/**
 * Representation of ClubMember objects.
 *
 * @author Amy Tang 20130856
 * @version 1.0
 * July 29, 2019
 */
public class ClubMember {

    /** Attributes of a ClubMember */
    private String name;  //name of Club Member
    private int age;  //age of Club Member
    private int distance;  //5, 10, 15, 30 km
    private boolean intensity; //true for run whole time, false for stop ish
    private String time;  //"morning", "afternoon", "evening"

    //constant list of valid distances
    private static final Set<Integer> DIST_KM = new HashSet<>(Arrays.asList(5,10,15,30));  //HashSet for high performance lookup

    //constant list of valid times of day
    private static final Set<String> TIME_DAY = new HashSet<>(Arrays.asList("morning","afternoon","evening","Morning","Afternoon","Evening"));  //is there a more efficient way to account for case-sensitive?

    /**
     * Class constructor.
     *
     * @param name (required) name of Joggr
     * @param age (required) age of Joggr
     * @param distance (required) preferred run distance of Joggr (4 options: 5km, 10km, 15km, 30km)
     * @param intensity (required) preferred intensity of Joggr (true for hard-core, false for people who stop)
     * @param time (required) preferred time of day to run (3 options: morning, afternoon, evening)
     * @throws IllegalClubMember warns for illegal inputs (age, distance, age) during creation or modification of ClubMember object
     */
    public ClubMember (String name, int age, int distance, boolean intensity, String time) throws IllegalClubMember {
        setName(name);
        setAge(age);
        setDistance(distance);
        setIntensity(intensity);
        setTime(time);
    } //end constructor

    /** Getters */
    public String getName() {  //getters are public bc needed in another class
        return this.name;
    }  //end method

    public int getAge() {
        return this.age;
    }  //end method

    public int getDistance() {
        return this.distance;
    }  //end method

    public boolean getIntensity() {
        return this.intensity;
    }  //end method

    public String getTime() {
        return this.time;
    }  //end method

    /** Setters */
    private void setName(String n) {
        this.name = n;
    }  //end method

    protected void setAge(int a) throws IllegalClubMember {
        if (a < 20) {
            throw new IllegalClubMember("Illegal age – runners can only be a member of the club if they are 20+ years old");
        }
        this.age = a;
    }  //end method

    /**
     * Method for grading; allows user to adjust preferred running distance
     * @param d (required) distance in km
     * @throws IllegalClubMember warns for illegal inputs (age, distance, age) during creation or modification of ClubMember object
     */
    protected void setDistance(int d) throws IllegalClubMember {
        if (!DIST_KM.contains(d)) {
            throw new IllegalClubMember("Illegal distance provided");
        }
        this.distance = d;
    }  //end method

    private void setIntensity(boolean i) {
        this.intensity = i;
    }  //end method

    private void setTime(String t) throws IllegalClubMember {
        if (!TIME_DAY.contains(t)) {
            throw new IllegalClubMember("Illegal time of day provided");
        }
        this.time = t;
    }  //end method

    @Override
    public String toString() {
        if (this.getIntensity()) {  //if intensity returns true
            return (getName() + " is " + getAge() + " years old and prefers to run " + getDistance() + "km non-stop in the " + getTime());
        } else {
            return (getName() + " is " + getAge() + " years old and prefers to run " + getDistance() + "km with breaks in the " + getTime());
        }  //end conditional
    }  //end method

    /**
     * Method determines whether two runners make ideal running partners, with the following criteria:
     *
     * <p> Same time of day for run
     * <p> Same age range (20-24, 25-29, 30-34, 35-39, 40-44, 45-49, 50-54, 55-59, 60-64, 65-69, 70+)
     * <p> Same distance
     * <p> Same intensity
     *
     * <p> Method is called on one Club Member and Matching should be equivalent if performed both ways.
     *
     * @param other (required) 2nd Club Member in comparison
     * @return true or false whether the two Club Members make ideal running partners
     */
    public boolean matchRunners(ClubMember other) {
        if ((getTime().equals(other.getTime()) && (getDistance() == other.getDistance()) && (getIntensity() == other.getIntensity()))) {
            //now deal with age ranges
            int lower;  //lower bound of age range; age ranges are inclusive
            int upper;  //upper bound of age range
            for (lower = 20; lower <= 65;) {  //start at minimum age & brings us up until 65-69 age group where low = 65
                upper = lower + 4;  //upper bound is 4 more than lower bound
                if (getAge() >= lower && other.getAge() >= lower && getAge() <= upper && other.getAge() <= upper) {  //if both in same age range
                    return true;
                } else if (getAge() >= 70 && other.getAge() >= 70) {  //no upper bound because ages 70+
                    return true;
                } else {
                    lower += 5;  //keep iterating through age ranges up until 64-69
                }
            }
        } return false;
    }  //end method

    /**
     *
     * @return list of ClubMember objects created from reading info in text file
     * @throws IllegalClubMember warns for illegal inputs of age, time, or distance during creation of ClubMember objects
     * @throws IOException warns for IOException in creation of ClubMember objects
     */
    public static List<ClubMember> readRunners() throws IllegalClubMember, IOException {
        BufferedReader br = null;
        String line = "";

        List<ClubMember> joggrList = new ArrayList<>();
        br = new BufferedReader(new FileReader("data/RunningClub.txt"));  //make sure no empty lines @ end to avoid ArrayIndexOutOfBoundsError 1

        while ((line = br.readLine())!= null) {
            String[] params = line.split(", | \n");
            joggrList.add(new ClubMember(params[0], Integer.parseInt(params[1].trim()), Integer.parseInt(params[2].trim()), Boolean.parseBoolean(params[3]), params[4]));  //use .trim() to avoid exceeding MAX_INT value
        }
        return joggrList;
    } //end method

    /**
     * Method reads file of all ClubMembers to find good running buddies based on matching conditions: age group, distance, intensity, time of day
     *
     * @param joggr (required) the Club Member who we are finding matches for
     * @return a list of possible running buddies for the Club Member accepted as param
     * @throws IllegalClubMember warns for illegal inputs of age, time, or distance during creation of ClubMember objects
     * @throws IOException warns for IOException in creation of ClubMember objects
     */
    public List<String> findMatches(ClubMember joggr) throws IllegalClubMember, IOException {
        List<ClubMember> joggrList = ClubMember.readRunners();
        List<String> joggrMatches = new ArrayList<>();  //Strings of runner's names
        for (ClubMember otherJoggr : joggrList) {
            if (joggr.matchRunners(otherJoggr) && !(joggr.getName()).equals(otherJoggr.getName())) {
                joggrMatches.add(otherJoggr.getName());
            }
        }
        return joggrMatches;
    }  //end method

    /**
     * Method prints out all possible good running pairs, assuring each pair is printed at most once.
     *
     * @param allJoggrs (required) List of all joggrs
     */
    public static void displayPairs(List<ClubMember> allJoggrs) {
        List<String[]> paired = new ArrayList<>();  //string array list because only appending names of pairs
        for (ClubMember joggr : allJoggrs) {  //O(n^2) time complexity, could i make this better?
            for (ClubMember otherJoggr : allJoggrs.subList(allJoggrs.indexOf(joggr)+1, allJoggrs.size())) {  //next jogger never compares with previous joggers to avoid duplicates and self-comparisons
                String[] pair = {joggr.getName(), otherJoggr.getName()};
                if (joggr.matchRunners(otherJoggr)) {  //don't need  !joggr.getName().equals(otherJoggr.getName()) because of subListed for loop
                    paired.add(pair);
                }  //end conditional
            }  //end inner for loop
        }  //end outer for loop

        for (String[] pair : paired) {
            System.out.println("Matched joggrs: " + pair[0] + " and " + pair[1]);
        }  //end loop
    }  //end method

}  //end program
