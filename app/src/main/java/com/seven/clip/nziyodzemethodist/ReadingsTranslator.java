package com.seven.clip.nziyodzemethodist;

class ReadingsTranslator {
    private String[] englishTitles = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};
    private String[] shonaTitles = {"Genesisi", "Ekisodo", "Revitiko", "Nhamba", "Duteronomi", "Joshua", "Vatongi", "Rute", "1 Samueri", "2 Samueri", "1 Madzimambo", "2 Madzimambo", "1 Makaronike", "2 Makaronike", "Ezira", "Nehemiya", "Esteri", "Jobo", "Mapisarema", "Zvirevo", "Muparidzi", "Rwiyo rukuru rwaSoromoni", "Izaya", "Jeremiya", "Kuwungudza kwaJeremiah", "Ezekieri", "Danieri", "Hozeya", "Joere", "Amosi", "Obadaya", "Jona", "Mika", "Nahumi", "Habakuki", "Zefanaya", "Hagai", "Zekaria", "Maraki", "Matewo", "Mako", "Ruka", "Johani", "Mabasa avaPostori", "VaRoma", "1 VaKorinde", "2 VaKorinde", "VaGaratia", "VaEfeso", "VaFiripi", "Vakorose", "1 VaTesaronika", "2 VaTesaronika", "1 Timoti", "2 Timoti", "Tito", "Firimoni", "VaHeberu", "Jakobho", "1 Petro", "2 Petro", "1 Johani", "2 Johani", "3 Johani", "Judha", "Zvakazarurwa"};

    ReadingsTranslator() {
    }

    String toShona(String title) {
        for (int i = 0; i < 66; i++) {
            if (title.contains(englishTitles[i])) {
                title = title.replace(englishTitles[i], shonaTitles[i]);
                break;
            }
        }
        return title;
    }
}
