package com.example.localization

/**
 * 16 Supported Indian Languages for Radha Jap Application
 * Covering official & widely spoken languages across North, South, East, West, and Central India.
 */
enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val region: String
) {
    HINDI("hi", "हिन्दी", "Hindi", "उत्तर / मध्य भारत"),
    BENGALI("bn", "বাংলা", "Bengali", "পশ্চিমবঙ্গ / ত্রিপুরা"),
    MARATHI("mr", "मराठी", "Marathi", "महाराष्ट्र"),
    TELUGU("te", "తెలుగు", "Telugu", "ఆంధ్రప్రదేశ్ / తెలంగాణ"),
    TAMIL("ta", "தமிழ்", "Tamil", "தமிழ்நாடு"),
    GUJARATI("gu", "ગુજરાતી", "Gujarati", "ગુજરાત"),
    KANNADA("kn", "ಕನ್ನಡ", "Kannada", "ಕರ್ನಾಟಕ"),
    MALAYALAM("ml", "മലയാളം", "Malayalam", "കേരളം"),
    ODIA("or", "ଓଡ଼ିଆ", "Odia", "ଓଡ଼ିଶା"),
    PUNJABI("pa", "ਪੰਜਾਬੀ", "Punjabi", "ਪੰਜਾਬ"),
    ASSAMESE("as", "অসমীয়া", "Assamese", "অসম"),
    SANSKRIT("sa", "संस्कृतम्", "Sanskrit", "देववाणी / अखिल भारत"),
    MAITHILI("mai", "मैथिली", "Maithili", "मिथिला / बिहार"),
    NEPALI("ne", "नेपाली", "Nepali", "सिक्किम / दार्जिलिङ"),
    KONKANI("kok", "कोंकणी", "Konkani", "गोवा / कोंकण"),
    ENGLISH("en", "English", "English", "Pan-India / Global");

    companion object {
        fun fromCode(code: String): AppLanguage {
            return values().firstOrNull { it.code.equals(code, ignoreCase = true) } ?: HINDI
        }
    }
}
