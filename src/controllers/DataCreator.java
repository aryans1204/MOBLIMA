package src.controllers;

import src.entities.Cinema;
import src.entities.Movie;
import src.entities.MovieStatus;
import src.entities.MovieType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataCreator {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        CinemaController cinema = new CinemaController("../../data/cinema.dat", "../../data/seat.dat");
        ClientController client = new ClientController("../../data/customer.dat", "../..//data/staff.dat");
        MovieController movie = new MovieController("../../data/movie.dat");

        //Create 12 MOVIES
        //Black panther
        String bpStr = "Lupita Nyong'o , Danai Gurira , Angela Bassett, Winston Duke, Letitia Wright , Florence Kasumba";
        ArrayList<String> bpCast = new ArrayList<String>(Arrays.asList(bpStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Black Panther: Wakanda Forever", MovieType.BLOCKBUSTER, MovieStatus.NOW_SHOWING,
                "Queen Ramonda, Shuri, M'Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T'Challa's death. As the Wakandans strive to embrace their next chapter, the heroes must band together with Nakia and Everett Ross to forge a new path for their beloved kingdom"
                , "Ryan Coogler", bpCast, "PG13", 161, LocalDate.parse("2022-11-10"), 0);

        //Black Adam
        String baStr = "Dwayne Johnson, Aldis Hodge, Pierce Brosnan, Noah Centineo, Sarah Shahi, Marwan Kenzari, Quintessa Swindell, Bodhi Sabongui";
        ArrayList<String> baCast = new ArrayList<String>(Arrays.asList(baStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Black Adam", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "Nearly 5,000 years after he was bestowed with the almighty powers of the Egyptian gods - and imprisoned just as quickly - Black Adam is freed from his earthly tomb, ready to unleash his unique form of justice on the modern world."
                , "Jaume Collet-Serra", baCast, "PG13", 125, LocalDate.parse("2022-10-20"), 0);

        //Gandhada Gudi
        String ggStr = "Amoghavarsha, Puneeth Rajkumar";
        ArrayList<String> ggCast = new ArrayList<String>(Arrays.asList(ggStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Gandhada Gudi", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "Mesmerized by the beauty of nature, a superstar sheds his stardom and explores the wonders of the land. While on this journey to explore his land, he ends up discovering new stories and unraveling some secrets."
                , "Amoghavarsha", ggCast, "PG", 97, LocalDate.parse("2022-10-28"), 0);

        //Avatar
        String atwowStr = "Vin Diesel, Kate Winslet, Zoe Saldana, Stephen Lang, Sam Worthington, Sigourney Weaver";
        ArrayList<String> atwowCast = new ArrayList<String>(Arrays.asList(atwowStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Avatar: The Way of Water", MovieType.TWO_D, MovieStatus.COMING_SOON,
                "Set more than a decade after the events of the first film, \"Avatar: The Way of Water\" begins to tell the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure."
                , "James Cameron", atwowCast, "TBA", 120, LocalDate.parse("2022-12-15"), 0);

        //Faces of Anne
        String foaStr = "Chutimon Chuengcharoensukying, Waruntorn Paonil , VioletteI Wautier, Phantira Pipityakorn, Arachaporn Pokinpakorn";
        ArrayList<String> foaCast = new ArrayList<String>(Arrays.asList(foaStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Faces Of Anne", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "Everyone is called “Anne” and they are all being chased! We challenge you to prove your bravery in a movie that will awaken all your senses in order to survive. In Faces of Anne, Anne takes you on a terrifying mystery journey with secrets lurking in the dark corners. When all the girls named \"Anne\" wakes up with fuzzy memories which the doctors and nurses keep injecting and saying it is mental symptoms that they think to themselves, along with hypnosis to make them remember who they are."
                        + " Not too long ago in this mental ward, every time Anne opens her eyes, every time Anne dies, or every time Anne's face changes, the young women in this place begin to mysteriously disappear. Fear gradually began to take root in the minds of the survivors. Until they encountered the legendary deer-headed demon \"Wedigo\". In order to survive, everyone must work together. The only way is to piece together the past and the mysteries that lead to the truth and to find a way to escape in time before death takes them away."
                , " Kongdej Jaturanrasamee, Rasiguet Sookkarn", foaCast, "NC16", 116, LocalDate.parse("2022-11-03"), 0);


        //SMILE
        String sStr = "Jesse T. Usher, Sosie Bacon, Kyle Gallner, Robin Weigert";
        ArrayList<String> sCast = new ArrayList<String>(Arrays.asList(sStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Smile", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "After witnessing a bizarre, traumatic incident involving a patient, Dr. Rose Cotter (Sosie Bacon) starts experiencing frightening occurrences that she can't explain. As an overwhelming terror begins taking over her life, Rose must confront her troubling past in order to survive and escape her horrifying new reality."
                , "Parker Finn", sCast, "M18", 115, LocalDate.parse("2022-09-29"), 0);

        //ONE PIECE FILM RED
        String opfrStr = "Ikue Otani, Kaori Nazuka, Mayumi Tanaka, Akemi Okamura, Kappei Yamaguchi, Hiroaki Hirata";
        ArrayList<String> opfrCast = new ArrayList<String>(Arrays.asList(opfrStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("ONE PIECE FILM RED", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "Uta - the most beloved singer in the world. Her voice, which she sings with while concealing her true identity, has been described as “otherworldly. She will appear in public for the first time at a live concert."
                        + " As the venue fills with all kinds of Uta fans - excited pirates, the Navy watching closely, and the Straw Hats led by Luffy who simply came to enjoy her sonorous performance - the voice that the whole world has been waiting for is about to resound. The story begins with the shocking fact that she is \"Shanks' daughter\"."
                , "Goro Taniguchi", opfrCast, "PG13", 115, LocalDate.parse("2022-09-29"), 0);

        //M3GAN
        String m3Str = "Allison Williams, Violet McGraw, Ronny Chieng, Brian Jordan Alvarez";
        ArrayList<String> m3Cast = new ArrayList<String>(Arrays.asList(m3Str.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("M3GAN", MovieType.TWO_D, MovieStatus.COMING_SOON,
                "M3GAN is a marvel of artificial intelligence, a lifelike doll that's programmed to be a child's greatest companion and a parent's greatest ally. Designed by Gemma, a brilliant roboticist, M3GAN can listen, watch and learn as it plays the role of friend and teacher, playmate and protector. When Gemma becomes the unexpected caretaker of her 8-year-old niece, she decides to give the girl an M3GAN prototype, a decision that leads to unimaginable consequences."
                , "Gerard Johnstone", m3Cast, "TBA", 120, LocalDate.parse("2023-01-05"), 0);


        //Love Today (Tamil)
        String ltStr = "Pradeep Ranganathan, Ivana";
        ArrayList<String> ltCast = new ArrayList<String>(Arrays.asList(ltStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Love Today (Tamil)", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "Imagine a world where the criteria to get married to your loved one is to explore each other's phone. A witty father lays such a trap to make his daughter realise how much she knows about her lover. The girl thinks she knows in and out of the boy and so does the boy. What happens from there is the rest of the movie."
                , "Pradeep Ranganathan", ltCast, "PG13", 153, LocalDate.parse("2022-11-04"), 0);

        //Hell Hole
        String hhStr = "Justin Cheung, Hedwig Tam, Jennifer Yu, Lam Yiu Sing, Raymond Chiu";
        ArrayList<String> hhCast = new ArrayList<String>(Arrays.asList(hhStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Hell Hole", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "This is a tale about love, loss, suffering and karma. A loving mother makes a death pact with a spirit, sacrificing herself to save her son. Years later, the son is bullied in a medical school and dies tragically. Now both mother and son have been reunited as vengeful spirits and are back to exact revenge on those who have wronged them."
                , "Sam Loh", hhCast, "NC16", 92, LocalDate.parse("2022-10-27"), 0);

        //Ajoomma
        String ajStr = "Hong Huifang, Jung Dong-Hwan, Kang Hyung Suk, Yeo Jingoo";
        ArrayList<String> ajCast = new ArrayList<String>(Arrays.asList(ajStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("Ajoomma", MovieType.TWO_D, MovieStatus.NOW_SHOWING,
                "Produced by award-winning filmmaker Anthony Chen."
                        + " Auntie (Hong Huifang), is a middle-aged Singaporean woman who has dedicated the best years of her life to caring for her family. Now widowed with her grown up son, Sam (Shane Pow) about to fly the roost, Auntie is left to contend with a whole new identity beyond her roles of daughter, wife, and mother."
                        + " A solo trip to Korea becomes a wild adventure for Auntie, where she meets Kwon-Woo (Kang Hyung Suk), a young tour guide who can’t seem to get his life in order, and Jung Su (Jung Dong-Hwan), an elderly security guard. The trio embark on an unexpected roller coaster ride where hearts flutter and unlikely bonds are formed."
                        + " Inspired by the director’s mother, AJOOMMA is the story of a woman’s journey of self-discovery, where Auntie learns to embrace her new independent life with renewed confidence and panache."
                , "He Shuming", ajCast, "NC16", 90, LocalDate.parse("2022-10-27"), 0);

        //The Woman King
        String twkStr = "John Boyega , Viola Davis, Thuso Mbedu, Lashana Lynch, Sheila Atim, Hero Fiennes Tiffin";
        ArrayList<String> twkCast = new ArrayList<String>(Arrays.asList(twkStr.trim().split("\\s*,\\s*")));

        movie.insertMovieToDB("The Woman King", MovieType.TWO_D, MovieStatus.COMING_SOON,
                "The Woman King is the remarkable story of the Agojie, the all-female unit of warriors who protected the African Kingdom of Dahomey in the 1800s with skills and a fierceness unlike anything the world has ever seen. Inspired by true events, The Woman King follows the emotionally epic journey of General Nanisca (Oscar-winner Viola Davis) as she trains the next generation of recruits and readies them for battle against an enemy determined to destroy their way of life. Some things are worth fighting for…"
                , "Gina Prince-Bythewood", m3Cast, "PG13", 135, LocalDate.parse("2023-02-02"), 0);

        //There is total of 12 movies in the database
        ArrayList<Movie> movies = movie.getAllMoviesFromDB();
        //Split movies accordingly for 3 cinemas. 4movies per cinema
        ArrayList<Movie> cinema1Mv = new ArrayList<Movie>();
        ArrayList<Movie> cinema2Mv = new ArrayList<Movie>();
        ArrayList<Movie> cinema3Mv = new ArrayList<Movie>();
        for (int i = 0; i < 4; i++) {
            cinema1Mv.add(movies.get(i));
            cinema2Mv.add(movies.get(i + 4));
            cinema3Mv.add(movies.get(i + 8));
        }
        //Create showtimes for each Cinema's Movies
        HashMap<String, ArrayList<LocalDateTime>> cinema1St = new HashMap<String, ArrayList<LocalDateTime>>();
        HashMap<String, ArrayList<LocalDateTime>> cinema2St = new HashMap<String, ArrayList<LocalDateTime>>();
        HashMap<String, ArrayList<LocalDateTime>> cinema3St = new HashMap<String, ArrayList<LocalDateTime>>();
        //Creating a fixed showtimes for all movies
        ArrayList<LocalDateTime> showtimes = new ArrayList<LocalDateTime>(Arrays.asList(
                LocalDateTime.parse("2022-11-14T09:30:00"), LocalDateTime.parse("2022-11-14T12:10:00"), LocalDateTime.parse("2022-11-14T15:50:00"), LocalDateTime.parse("2022-11-14T18:10:00"), LocalDateTime.parse("2022-11-14T21:00:00"),//mon
                LocalDateTime.parse("2022-11-15T09:30:00"), LocalDateTime.parse("2022-11-15T12:10:00"), LocalDateTime.parse("2022-11-15T15:50:00"), LocalDateTime.parse("2022-11-15T18:10:00"), LocalDateTime.parse("2022-11-15T21:00:00"),//tue
                LocalDateTime.parse("2022-11-16T09:30:00"), LocalDateTime.parse("2022-11-16T12:10:00"), LocalDateTime.parse("2022-11-16T15:50:00"), LocalDateTime.parse("2022-11-16T18:10:00"), LocalDateTime.parse("2022-11-16T21:00:00"),//wed
                LocalDateTime.parse("2022-11-17T09:30:00"), LocalDateTime.parse("2022-11-17T12:10:00"), LocalDateTime.parse("2022-11-17T15:50:00"), LocalDateTime.parse("2022-11-17T18:10:00"), LocalDateTime.parse("2022-11-17T21:00:00"),//thur
                LocalDateTime.parse("2022-11-18T09:30:00"), LocalDateTime.parse("2022-11-18T12:10:00"), LocalDateTime.parse("2022-11-18T15:50:00"), LocalDateTime.parse("2022-11-18T18:10:00"), LocalDateTime.parse("2022-11-18T21:00:00"),//fri
                LocalDateTime.parse("2022-11-19T09:30:00"), LocalDateTime.parse("2022-11-19T12:10:00"), LocalDateTime.parse("2022-11-19T15:50:00"), LocalDateTime.parse("2022-11-19T18:10:00"), LocalDateTime.parse("2022-11-19T21:00:00"),//sat
                LocalDateTime.parse("2022-11-20T09:30:00"), LocalDateTime.parse("2022-11-20T12:10:00"), LocalDateTime.parse("2022-11-20T15:50:00"), LocalDateTime.parse("2022-11-20T18:10:00"), LocalDateTime.parse("2022-11-20T21:00:00") //sun
        ));
        //Assign the showtime for each movie
        for (int i = 0; i < 4; i++) {
            cinema1St.put(cinema1Mv.get(i).getTitle(), showtimes);
            cinema1St.put(cinema2Mv.get(i).getTitle(), showtimes);
            cinema1St.put(cinema3Mv.get(i).getTitle(), showtimes);
        }
        cinema.insertCinemaIntoDB("CathayTheatre1", cinema1Mv, cinema1St);
        cinema.insertCinemaIntoDB("CathayTheatre2", cinema2Mv, cinema2St);
        cinema.insertCinemaIntoDB("CathayTheatre3", cinema3Mv, cinema3St);

        //Create CUSTOMERS
        client.insertCustomerToDB("John", 19, "john", "test123", "john111@gmail.com", 84324783);
        client.insertCustomerToDB("Mary", 56, "mary", "test123", "mary222@gmail.com", 81357852);
        client.insertCustomerToDB("Elizabeth", 25, "elizabeth", "test123", "eliz333@gmail.com", 94327821);
        client.insertCustomerToDB("Ben", 65, "ben", "test123", "ben444@gmail.com", 83178542);
        client.insertCustomerToDB("Jeffrey", 18, "jeffrey", "test123", "jeff555@gmail.com", 96217568);

        //Create Seats
        //Not sure how this will be done

        //Create STAFF
        ArrayList<Cinema> cinemas = cinema.getAllCinemasFromDB();
        client.insertStaffToDB("jake", "test123", cinemas.get(0));
        client.insertStaffToDB("henry", "test123", cinemas.get(1));
        client.insertStaffToDB("jane", "test123", cinemas.get(2));
    }

}
