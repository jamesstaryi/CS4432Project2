James Yi - 663227861

Section I:
    (in console)
    javac Main.java
    java Main
    Use one of the following commands after (all v values need to be 4 digits long, so 20 for example needs to be typed as 0020)
        CREATE INDEX ON Project2Dataset (RandomV)
        SELECT * FROM Project2Dataset WHERE RandomV = v
        SELECT * FROM Project2Dataset WHERE RandomV > v1 AND RandomV < v2
        SELECT * FROM Project2Dataset WHERE RandomV != v
        QUIT (To exit the program)
            e.g SELECT * FROM Project2Dataset WHERE RandomV > 1042 AND RandomV < 1052

Section II:
    From what I can tell everything works correctly.
    There is an issue where the output isnt ordered the way I intended it too but thats due to how Java's Arrays.sort works.
        e.g F13-Rec015, Name015, address015, 4037...
            F01-Rec001, Name001, address001, 4037...
            F50-Rec064, Name064, address064, 4037...
        it puts F13 first and the F01.