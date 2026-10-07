package com.example.localization

import com.example.audio.SoundType
import com.example.data.preferences.HapticStrength
import com.example.data.preferences.TapAreaMode

data class LanguageStrings(
    val appName: String,
    val tabJap: String,
    val tabSadhana: String,
    val tabGoal: String,
    val selectMantra: String,
    val thisMantraMala: String,
    val bead: String,
    val totalToday: String,
    val tapToChant: String,
    val resetMala: String,
    val undo: String,
    val sound: String,
    val dhyanMode: String,
    val todaySadhanaSummary: String,
    val totalChantedToday: String,
    val malas: String,
    val beads: String,
    val sankalpaTarget: String,
    val streak: String,
    val days: String,
    val mantraWiseRecords: String,
    val lifetimeTotal: String,
    val lifetimeMalas: String,
    val lifetimeBeads: String,
    val dhyanTitle: String,
    val soundDrone: String,
    val tapAnywherePrompt: String,
    val exitDhyan: String,
    val exitTitle: String,
    val exitMessage: String,
    val todayCompletedMalas: String,
    val continueJap: String,
    val confirmExit: String,
    val celebrationTitle: String,
    val celebrationDesc: String,
    val acceptCelebration: String,
    val settingsTitle: String,
    val settingsSubtitle: String,
    val languageTitle: String,
    val dailyGoalTitle: String,
    val customGoal: String,
    val customMantra: String,
    val soundFeedback: String,
    val vibrationFeedback: String,
    val touchHardware: String,
    val tapAnywhere: String,
    val tapAnywhereDesc: String,
    val volumeKeys: String,
    val volumeKeysDesc: String,
    val themeTitle: String,
    val themeGlass: String,
    val themeLight: String,
    val themeDark: String,
    val themeAuto: String,
    val devotionalFooter: String,
    val blessing: String,
    val customMantraDialogTitle: String,
    val enterMantra: String,
    val submit: String,
    val cancel: String,
    val presetMantras: List<String>,
    val devotionalQuotes: List<String>
)

object LanguageProvider {

    fun getStrings(language: AppLanguage): LanguageStrings {
        return when (language) {
            AppLanguage.HINDI -> hindiStrings
            AppLanguage.BENGALI -> bengaliStrings
            AppLanguage.MARATHI -> marathiStrings
            AppLanguage.TELUGU -> teluguStrings
            AppLanguage.TAMIL -> tamilStrings
            AppLanguage.GUJARATI -> gujaratiStrings
            AppLanguage.KANNADA -> kannadaStrings
            AppLanguage.MALAYALAM -> malayalamStrings
            AppLanguage.ODIA -> odiaStrings
            AppLanguage.PUNJABI -> punjabiStrings
            AppLanguage.ASSAMESE -> assameseStrings
            AppLanguage.SANSKRIT -> sanskritStrings
            AppLanguage.MAITHILI -> maithiliStrings
            AppLanguage.NEPALI -> nepaliStrings
            AppLanguage.KONKANI -> konkaniStrings
            AppLanguage.ENGLISH -> englishStrings
        }
    }

    fun getSoundTitle(language: AppLanguage, sound: SoundType): String {
        return when (language) {
            AppLanguage.BENGALI -> when (sound) {
                SoundType.BELL -> "ঘণ্টা (Temple Bell)"
                SoundType.FLUTE -> "বাঁশি (Flute)"
                SoundType.SHANKH -> "শঙ্খ (Shankh)"
                SoundType.TULSI_BEAD -> "তুলসী দানা (Tulsi Bead)"
                SoundType.SILENT -> "নীরব (Silent)"
            }
            AppLanguage.GUJARATI -> when (sound) {
                SoundType.BELL -> "ઘંટડી (Temple Bell)"
                SoundType.FLUTE -> "વાંસળી (Flute)"
                SoundType.SHANKH -> "શંખ (Shankh)"
                SoundType.TULSI_BEAD -> "તુલસી મણકો (Tulsi Bead)"
                SoundType.SILENT -> "મૌન (Silent)"
            }
            AppLanguage.MARATHI -> when (sound) {
                SoundType.BELL -> "घंटी (Temple Bell)"
                SoundType.FLUTE -> "बासरी (Flute)"
                SoundType.SHANKH -> "शंख (Shankh)"
                SoundType.TULSI_BEAD -> "तुळशी मणी (Tulsi Bead)"
                SoundType.SILENT -> "शांत (Silent)"
            }
            AppLanguage.TELUGU -> when (sound) {
                SoundType.BELL -> "గంట (Temple Bell)"
                SoundType.FLUTE -> "వేణువు (Flute)"
                SoundType.SHANKH -> "శంఖం (Shankh)"
                SoundType.TULSI_BEAD -> "తులసి పూస (Tulsi Bead)"
                SoundType.SILENT -> "మౌనం (Silent)"
            }
            AppLanguage.TAMIL -> when (sound) {
                SoundType.BELL -> "மணி (Temple Bell)"
                SoundType.FLUTE -> "புல்லாங்குழல் (Flute)"
                SoundType.SHANKH -> "சங்கு (Shankh)"
                SoundType.TULSI_BEAD -> "துளசி மணி (Tulsi Bead)"
                SoundType.SILENT -> "மௌனம் (Silent)"
            }
            AppLanguage.KANNADA -> when (sound) {
                SoundType.BELL -> "ಗಂಟೆ (Temple Bell)"
                SoundType.FLUTE -> "ಕೊಳಲು (Flute)"
                SoundType.SHANKH -> "ಶಂಖ (Shankh)"
                SoundType.TULSI_BEAD -> "ತುಳಸಿ ಮಣಿ (Tulsi Bead)"
                SoundType.SILENT -> "ಮೌನ (Silent)"
            }
            AppLanguage.MALAYALAM -> when (sound) {
                SoundType.BELL -> "മണി (Temple Bell)"
                SoundType.FLUTE -> "ഓടക്കുഴൽ (Flute)"
                SoundType.SHANKH -> "ശംഖ് (Shankh)"
                SoundType.TULSI_BEAD -> "തുളസി മണി (Tulsi Bead)"
                SoundType.SILENT -> "മൗനം (Silent)"
            }
            AppLanguage.ODIA -> when (sound) {
                SoundType.BELL -> "ଘଣ୍ଟି (Temple Bell)"
                SoundType.FLUTE -> "ବଂଶୀ (Flute)"
                SoundType.SHANKH -> "ଶଙ୍ଖ (Shankh)"
                SoundType.TULSI_BEAD -> "ତୁଳସୀ ଦାନା (Tulsi Bead)"
                SoundType.SILENT -> "ମୌନ (Silent)"
            }
            AppLanguage.PUNJABI -> when (sound) {
                SoundType.BELL -> "ਘੰਟੀ (Temple Bell)"
                SoundType.FLUTE -> "ਬੰਸਰੀ (Flute)"
                SoundType.SHANKH -> "ਸ਼ੰਖ (Shankh)"
                SoundType.TULSI_BEAD -> "ਤੁਲਸੀ ਮਣਕਾ (Tulsi Bead)"
                SoundType.SILENT -> "ਚੁੱਪ (Silent)"
            }
            AppLanguage.ASSAMESE -> when (sound) {
                SoundType.BELL -> "ঘণ্টী (Temple Bell)"
                SoundType.FLUTE -> "বাঁহী (Flute)"
                SoundType.SHANKH -> "শংখ (Shankh)"
                SoundType.TULSI_BEAD -> "তুলসী গুটি (Tulsi Bead)"
                SoundType.SILENT -> "মৌন (Silent)"
            }
            AppLanguage.SANSKRIT -> when (sound) {
                SoundType.BELL -> "घण्टानादः (Temple Bell)"
                SoundType.FLUTE -> "वेणुनादः (Flute)"
                SoundType.SHANKH -> "शङ्खध्वनिः (Shankh)"
                SoundType.TULSI_BEAD -> "तुलसीमणिः (Tulsi Bead)"
                SoundType.SILENT -> "मौनम् (Silent)"
            }
            AppLanguage.MAITHILI -> when (sound) {
                SoundType.BELL -> "घंटी (Temple Bell)"
                SoundType.FLUTE -> "बाँसुरी (Flute)"
                SoundType.SHANKH -> "शंख (Shankh)"
                SoundType.TULSI_BEAD -> "तुलसी दाना (Tulsi Bead)"
                SoundType.SILENT -> "मौन (Silent)"
            }
            AppLanguage.NEPALI -> when (sound) {
                SoundType.BELL -> "घन्टी (Temple Bell)"
                SoundType.FLUTE -> "बाँसुरी (Flute)"
                SoundType.SHANKH -> "शङ्ख (Shankh)"
                SoundType.TULSI_BEAD -> "तुलसी गेडा (Tulsi Bead)"
                SoundType.SILENT -> "मौन (Silent)"
            }
            AppLanguage.KONKANI -> when (sound) {
                SoundType.BELL -> "घांट (Temple Bell)"
                SoundType.FLUTE -> "बांसरी (Flute)"
                SoundType.SHANKH -> "शंख (Shankh)"
                SoundType.TULSI_BEAD -> "तुळशी मणी (Tulsi Bead)"
                SoundType.SILENT -> "मौन (Silent)"
            }
            AppLanguage.ENGLISH -> when (sound) {
                SoundType.BELL -> "Temple Bell"
                SoundType.FLUTE -> "Flute"
                SoundType.SHANKH -> "Sacred Conch"
                SoundType.TULSI_BEAD -> "Tulsi Bead (Wood Click)"
                SoundType.SILENT -> "Silent"
            }
            AppLanguage.HINDI -> when (sound) {
                SoundType.BELL -> "घंटी (Temple Bell)"
                SoundType.FLUTE -> "बांसुरी (Flute)"
                SoundType.SHANKH -> "शंख (Shankh)"
                SoundType.TULSI_BEAD -> "तुलसी मनका (Tulsi Bead)"
                SoundType.SILENT -> "मौन (Silent)"
            }
        }
    }

    fun getHapticTitle(language: AppLanguage, strength: HapticStrength): String {
        return when (language) {
            AppLanguage.BENGALI -> when (strength) {
                HapticStrength.OFF -> "বন্ধ"
                HapticStrength.GENTLE -> "মৃদু"
                HapticStrength.MEDIUM -> "মাঝারি"
                HapticStrength.STRONG -> "তীব্র"
            }
            AppLanguage.GUJARATI -> when (strength) {
                HapticStrength.OFF -> "બંધ"
                HapticStrength.GENTLE -> "હળવું"
                HapticStrength.MEDIUM -> "મધ્યમ"
                HapticStrength.STRONG -> "તીવ્ર"
            }
            AppLanguage.MARATHI -> when (strength) {
                HapticStrength.OFF -> "बंद"
                HapticStrength.GENTLE -> "हलके"
                HapticStrength.MEDIUM -> "मध्यम"
                HapticStrength.STRONG -> "तीव्र"
            }
            AppLanguage.TELUGU -> when (strength) {
                HapticStrength.OFF -> "ఆఫ్"
                HapticStrength.GENTLE -> "మృదువైన"
                HapticStrength.MEDIUM -> "మధ్యస్థ"
                HapticStrength.STRONG -> "తీవ్ర"
            }
            AppLanguage.TAMIL -> when (strength) {
                HapticStrength.OFF -> "ஆஃப்"
                HapticStrength.GENTLE -> "மென்மை"
                HapticStrength.MEDIUM -> "நடுத்தர"
                HapticStrength.STRONG -> "தீவிர"
            }
            AppLanguage.KANNADA -> when (strength) {
                HapticStrength.OFF -> "ಆಫ್"
                HapticStrength.GENTLE -> "ಮೃದು"
                HapticStrength.MEDIUM -> "ಮಧ್ಯಮ"
                HapticStrength.STRONG -> "ತೀವ್ರ"
            }
            AppLanguage.MALAYALAM -> when (strength) {
                HapticStrength.OFF -> "ഓഫ്"
                HapticStrength.GENTLE -> "മൃദു"
                HapticStrength.MEDIUM -> "ഇടത്തരം"
                HapticStrength.STRONG -> "ശക്തം"
            }
            AppLanguage.ODIA -> when (strength) {
                HapticStrength.OFF -> "ବନ୍ଦ"
                HapticStrength.GENTLE -> "କୋମଳ"
                HapticStrength.MEDIUM -> "ମଧ୍ୟମ"
                HapticStrength.STRONG -> "ତୀବ୍ର"
            }
            AppLanguage.PUNJABI -> when (strength) {
                HapticStrength.OFF -> "ਬੰਦ"
                HapticStrength.GENTLE -> "ਹਲਕਾ"
                HapticStrength.MEDIUM -> "ਦਰਮਿਆਨਾ"
                HapticStrength.STRONG -> "ਤੇਜ਼"
            }
            AppLanguage.ASSAMESE -> when (strength) {
                HapticStrength.OFF -> "বন্ধ"
                HapticStrength.GENTLE -> "কোমল"
                HapticStrength.MEDIUM -> "মধ্যম"
                HapticStrength.STRONG -> "তীব্ৰ"
            }
            AppLanguage.SANSKRIT -> when (strength) {
                HapticStrength.OFF -> "विरामः"
                HapticStrength.GENTLE -> "मृदुः"
                HapticStrength.MEDIUM -> "मध्यमः"
                HapticStrength.STRONG -> "तीव्रः"
            }
            AppLanguage.MAITHILI -> when (strength) {
                HapticStrength.OFF -> "बन्द"
                HapticStrength.GENTLE -> "हलुक"
                HapticStrength.MEDIUM -> "मध्यम"
                HapticStrength.STRONG -> "तीव्र"
            }
            AppLanguage.NEPALI -> when (strength) {
                HapticStrength.OFF -> "बन्द"
                HapticStrength.GENTLE -> "हल्का"
                HapticStrength.MEDIUM -> "मध्यम"
                HapticStrength.STRONG -> "तीव्र"
            }
            AppLanguage.KONKANI -> when (strength) {
                HapticStrength.OFF -> "बंद"
                HapticStrength.GENTLE -> "संवळ"
                HapticStrength.MEDIUM -> "मध्यम"
                HapticStrength.STRONG -> "तीव्र"
            }
            AppLanguage.ENGLISH -> when (strength) {
                HapticStrength.OFF -> "Off"
                HapticStrength.GENTLE -> "Gentle"
                HapticStrength.MEDIUM -> "Medium"
                HapticStrength.STRONG -> "Strong"
            }
            AppLanguage.HINDI -> when (strength) {
                HapticStrength.OFF -> "बंद"
                HapticStrength.GENTLE -> "हल्का"
                HapticStrength.MEDIUM -> "मध्यम"
                HapticStrength.STRONG -> "तीव्र"
            }
        }
    }

    fun getImmersiveModeTitle(language: AppLanguage): String {
        return when (language) {
            AppLanguage.BENGALI -> "ফুল স্ক্রিন নিমগ্ন মোড (Hide Status Bar)"
            AppLanguage.GUJARATI -> "ફુલ સ્ક્રીન ઇમર્સિવ મોડ (Hide Status Bar)"
            AppLanguage.MARATHI -> "पूर्ण स्क्रीन मोड (Hide Status Bar)"
            AppLanguage.TELUGU -> "పూర్తి స్క్రీన్ మోడ్ (Hide Status Bar)"
            AppLanguage.TAMIL -> "முழுத்திரை முறை (Hide Status Bar)"
            AppLanguage.KANNADA -> "ಪೂರ್ಣ ಸ್ಕ್ರೀನ್ ಮೋಡ್ (Hide Status Bar)"
            AppLanguage.MALAYALAM -> "ഫുൾ സ്ക്രീൻ മോഡ് (Hide Status Bar)"
            AppLanguage.ODIA -> "ପୂର୍ଣ୍ଣ ସ୍କ୍ରିନ୍ ମୋଡ୍ (Hide Status Bar)"
            AppLanguage.PUNJABI -> "ਫੁੱਲ ਸਕ੍ਰੀਨ ਮੋਡ (Hide Status Bar)"
            AppLanguage.ASSAMESE -> "পূৰ্ণ স্ক্ৰীন মোড (Hide Status Bar)"
            AppLanguage.SANSKRIT -> "पूर्णपटलध्यानम् (Hide Status Bar)"
            AppLanguage.MAITHILI -> "फुल स्क्रीन इमर्सिव मोड (Hide Status Bar)"
            AppLanguage.NEPALI -> "फुल स्क्रिन मोड (Hide Status Bar)"
            AppLanguage.KONKANI -> "फुल स्क्रीन मोड (Hide Status Bar)"
            AppLanguage.ENGLISH -> "Full Screen Immersive Mode (Hide Status Bar)"
            AppLanguage.HINDI -> "फुल स्क्रीन इमर्सिव मोड (Hide Status Bar)"
        }
    }

    fun getImmersiveModeDesc(language: AppLanguage): String {
        return when (language) {
            AppLanguage.BENGALI -> "ব্যাটারি, ঘড়ি ও স্ট্যাটাস বার লুকিয়ে একাগ্র জপ"
            AppLanguage.GUJARATI -> "બેટરી, ઘડિયાળ અને સ્ટેટસ બાર છુપાવી શાંત ધ્યાન"
            AppLanguage.MARATHI -> "बॅटरी, घड्याळ व स्टेटस बार लपवून शांत ध्यान"
            AppLanguage.TELUGU -> "బ్యాటరీ, గడియారం దాచి ప్రశాంత ధ్యానం"
            AppLanguage.TAMIL -> "கவனச்சிதறல் இன்றி ஜபிக்க நிலைப் பட்டையை மறைக்கவும்"
            AppLanguage.KANNADA -> "ಬ್ಯಾಟರಿ, ಗಡಿಯಾರ ಮರೆಮಾಡಿ ಶಾಂತ ಜಪ"
            AppLanguage.MALAYALAM -> "സ്റ്റാറ്റസ് ബാർ മറച്ച് ശാന്തമായ ജപം"
            AppLanguage.ODIA -> "ଶାନ୍ତ ଧ୍ୟାନ ପାଇଁ ଷ୍ଟାଟସ୍ ବାର୍ ଲୁଚାନ୍ତୁ"
            AppLanguage.PUNJABI -> "ਸ਼ਾਂਤ ਧਿਆਨ ਲਈ ਸਟੇਟਸ ਬਾਰ ਲੁਕਾਓ"
            AppLanguage.ASSAMESE -> "শান্ত ধ্যানৰ বাবে ষ্টেটাছ বাৰ লুকুৱাওক"
            AppLanguage.SANSKRIT -> "स्थितिरेखामन्तर्धाय प्रशान्तजपः"
            AppLanguage.MAITHILI -> "शांतिपूर्ण ध्यान लेल स्टेटस बार छुपाउ"
            AppLanguage.NEPALI -> "शान्त ध्यानका लागि स्टेटस बार लुकाउनुहोस्"
            AppLanguage.KONKANI -> "शांतीच्या ध्याना खातीर स्टेटस बार लिपयात"
            AppLanguage.ENGLISH -> "Hide battery, clock & status bar for peaceful focus"
            AppLanguage.HINDI -> "बैटरी, घड़ी व स्टेटस बार छुपाकर शांतिपूर्ण ध्यान"
        }
    }

    fun getHistoryChartTitle(language: AppLanguage): String {
        return when (language) {
            AppLanguage.BENGALI -> "গত ৭ দিনের জপ (7 Days History)"
            AppLanguage.GUJARATI -> "છેલ્લા 7 દિવસનો જપ (7 Days History)"
            AppLanguage.MARATHI -> "मागील 7 दिवसांचा जप (7 Days History)"
            AppLanguage.TELUGU -> "గత 7 రోజుల జప చరిత్ర (7 Days History)"
            AppLanguage.TAMIL -> "கடந்த 7 நாட்கள் ஜப வரலாறு (7 Days History)"
            AppLanguage.KANNADA -> "ಕಳೆದ 7 ದಿನಗಳ ಜಪ ಇತಿಹಾಸ (7 Days History)"
            AppLanguage.MALAYALAM -> "കഴിഞ്ഞ 7 ദിവസങ്ങളിലെ ജപം (7 Days History)"
            AppLanguage.ODIA -> "ଗତ ୭ ଦିନର ଜପ ଇତିହାସ (7 Days History)"
            AppLanguage.PUNJABI -> "ਪਿਛਲੇ 7 ਦਿਨਾਂ ਦਾ ਜਪ (7 Days History)"
            AppLanguage.ASSAMESE -> "যোৱা ৭ দিনৰ জপ (7 Days History)"
            AppLanguage.SANSKRIT -> "विगतसप्तदिनानां जपवृत्तम् (7 Days History)"
            AppLanguage.MAITHILI -> "पिछला ७ दिनक जप (7 Days History)"
            AppLanguage.NEPALI -> "पछिल्लो ७ दिनको जप (7 Days History)"
            AppLanguage.KONKANI -> "फाटल्या 7 दिसांचो जप (7 Days History)"
            AppLanguage.ENGLISH -> "Last 7 Days Chanting (7 Days History)"
            AppLanguage.HINDI -> "पिछले 7 दिनों का जप (7 Days History)"
        }
    }

    fun getTargetGoalLabel(language: AppLanguage, target: Int): String {
        val s = getStrings(language)
        return "${s.sankalpaTarget}: $target ${s.malas}"
    }

    fun getActiveMalaLabel(language: AppLanguage, beadProgress: Int): String {
        return when (language) {
            AppLanguage.BENGALI -> "চলতি মালা: $beadProgress/108"
            AppLanguage.GUJARATI -> "ચાલુ માળા: $beadProgress/108"
            AppLanguage.MARATHI -> "चालू माळ: $beadProgress/108"
            AppLanguage.TELUGU -> "ప్రస్తుత మాల: $beadProgress/108"
            AppLanguage.TAMIL -> "தற்போதைய மாலை: $beadProgress/108"
            AppLanguage.KANNADA -> "ಪ್ರಸ್ತುತ ಮಾಲೆ: $beadProgress/108"
            AppLanguage.MALAYALAM -> "നിലവിലെ മാല: $beadProgress/108"
            AppLanguage.ODIA -> "ଚାଲୁ ମାଳା: $beadProgress/108"
            AppLanguage.PUNJABI -> "ਚੱਲ ਰਹੀ ਮਾਲਾ: $beadProgress/108"
            AppLanguage.ASSAMESE -> "চলতি মালা: $beadProgress/108"
            AppLanguage.SANSKRIT -> "प्रवृत्ता माला: $beadProgress/१०८"
            AppLanguage.MAITHILI -> "चालू माला: $beadProgress/108"
            AppLanguage.NEPALI -> "चालु माला: $beadProgress/108"
            AppLanguage.KONKANI -> "चालू माळ: $beadProgress/108"
            AppLanguage.ENGLISH -> "Current Mala: $beadProgress/108"
            AppLanguage.HINDI -> "चालू माला: $beadProgress/108"
        }
    }

    fun getTodayContributionLabel(language: AppLanguage, percent: Int): String {
        return when (language) {
            AppLanguage.BENGALI -> "আজকের অবদান: $percent%"
            AppLanguage.GUJARATI -> "આજનું યોગદાન: $percent%"
            AppLanguage.MARATHI -> "आजचे योगदान: $percent%"
            AppLanguage.TELUGU -> "నేటి వాటా: $percent%"
            AppLanguage.TAMIL -> "இன்றைய பங்கு: $percent%"
            AppLanguage.KANNADA -> "ಇಂದಿನ ಪಾಲು: $percent%"
            AppLanguage.MALAYALAM -> "ഇന്നത്തെ പങ്ക്: $percent%"
            AppLanguage.ODIA -> "ଆଜିର ଯୋଗଦାନ: $percent%"
            AppLanguage.PUNJABI -> "ਅੱਜ ਦਾ ਯੋਗਦਾਨ: $percent%"
            AppLanguage.ASSAMESE -> "আজিৰ অৱদান: $percent%"
            AppLanguage.SANSKRIT -> "अद्यतनभागः: $percent%"
            AppLanguage.MAITHILI -> "आइ के योगदान: $percent%"
            AppLanguage.NEPALI -> "आजको योगदान: $percent%"
            AppLanguage.KONKANI -> "आयचो वांटो: $percent%"
            AppLanguage.ENGLISH -> "Today's Share: $percent%"
            AppLanguage.HINDI -> "आज का योगदान: $percent%"
        }
    }

    fun getTotalBeadsChantedLabel(language: AppLanguage, totalBeads: Int): String {
        val s = getStrings(language)
        return when (language) {
            AppLanguage.BENGALI -> "মোট $totalBeads ${s.beads} জপ"
            AppLanguage.GUJARATI -> "કુલ $totalBeads ${s.beads} જપ"
            AppLanguage.MARATHI -> "एकूण $totalBeads ${s.beads} जप"
            AppLanguage.TELUGU -> "మొత్తం $totalBeads ${s.beads} జపం"
            AppLanguage.TAMIL -> "மொத்தம் $totalBeads ${s.beads} ஜபம்"
            AppLanguage.KANNADA -> "ಒಟ್ಟು $totalBeads ${s.beads} ಜಪ"
            AppLanguage.MALAYALAM -> "ആകെ $totalBeads ${s.beads} ജപം"
            AppLanguage.ODIA -> "ମୋଟ $totalBeads ${s.beads} ଜପ"
            AppLanguage.PUNJABI -> "ਕੁੱਲ $totalBeads ${s.beads} ਜਪ"
            AppLanguage.ASSAMESE -> "মুঠ $totalBeads ${s.beads} জপ"
            AppLanguage.SANSKRIT -> "आहत्य $totalBeads ${s.beads} जपः"
            AppLanguage.MAITHILI -> "कुल $totalBeads ${s.beads} जप"
            AppLanguage.NEPALI -> "जम्मा $totalBeads ${s.beads} जप"
            AppLanguage.KONKANI -> "एकूण $totalBeads ${s.beads} जप"
            AppLanguage.ENGLISH -> "Total $totalBeads ${s.beads} Chanted"
            AppLanguage.HINDI -> "कुल $totalBeads ${s.beads} जप"
        }
    }

    fun getStartFirstJapLabel(language: AppLanguage): String {
        return when (language) {
            AppLanguage.BENGALI -> "আজকের প্রথম জপ শুরু করুন!"
            AppLanguage.GUJARATI -> "આજનો પ્રથમ જપ શરૂ કરો!"
            AppLanguage.MARATHI -> "आजचा पहिला जप सुरू करा!"
            AppLanguage.TELUGU -> "నేటి మొదటి జపం ప్రారంభించండి!"
            AppLanguage.TAMIL -> "இன்றைய முதல் ஜபத்தைத் தொடங்குங்கள்!"
            AppLanguage.KANNADA -> "ಇಂದಿನ ಮೊದಲ ಜಪ ಪ್ರಾರಂಭಿಸಿ!"
            AppLanguage.MALAYALAM -> "ഇന്നത്തെ ആദ്യ ജപം ആരംഭിക്കുക!"
            AppLanguage.ODIA -> "ଆଜିର ପ୍ରଥମ ଜପ ଆରମ୍ଭ କରନ୍ତୁ!"
            AppLanguage.PUNJABI -> "ਅੱਜ ਪਹਿਲਾ ਜਪ ਸ਼ੁਰੂ ਕਰੋ!"
            AppLanguage.ASSAMESE -> "আজি প্ৰথম জপ আৰম্ভ কৰক!"
            AppLanguage.SANSKRIT -> "अद्य प्रथमं जपमारभताम्!"
            AppLanguage.MAITHILI -> "आइ पहिल जप आरम्भ करू!"
            AppLanguage.NEPALI -> "आज पहिलो जप आरम्भ गर्नुहोस्!"
            AppLanguage.KONKANI -> "आयचो पयलो जप सुरू करात!"
            AppLanguage.ENGLISH -> "Begin today's first chanting!"
            AppLanguage.HINDI -> "आज प्रथम जप आरम्भ करें!"
        }
    }

    fun getTargetLabel(language: AppLanguage): String {
        return when (language) {
            AppLanguage.BENGALI -> "লক্ষ্য"
            AppLanguage.GUJARATI -> "લક્ષ્ય"
            AppLanguage.MARATHI -> "ध्येय"
            AppLanguage.TELUGU -> "లక్ష్యం"
            AppLanguage.TAMIL -> "இலக்கு"
            AppLanguage.KANNADA -> "ಗುರಿ"
            AppLanguage.MALAYALAM -> "ലക്ഷ്യം"
            AppLanguage.ODIA -> "ଲକ୍ଷ୍ୟ"
            AppLanguage.PUNJABI -> "ਟੀਚਾ"
            AppLanguage.ASSAMESE -> "লক্ষ্য"
            AppLanguage.SANSKRIT -> "लक्ष्यम्"
            AppLanguage.MAITHILI -> "लक्ष्य"
            AppLanguage.NEPALI -> "लक्ष्य"
            AppLanguage.KONKANI -> "ध्येय"
            AppLanguage.ENGLISH -> "Goal"
            AppLanguage.HINDI -> "लक्ष्य"
        }
    }

    private val hindiStrings = LanguageStrings(
        appName = "श्री राधा नाम जप",
        tabJap = "जप (Jap)",
        tabSadhana = "साधना (Sadhana)",
        tabGoal = "संकल्प (Goal)",
        selectMantra = "मंत्र चुनें",
        thisMantraMala = "इस मंत्र की माला",
        bead = "मनका",
        totalToday = "आज कुल",
        tapToChant = "स्पर्श करें (Tap)",
        resetMala = "माला रीसेट",
        undo = "पूर्ववत (Undo)",
        sound = "ध्वनि",
        dhyanMode = "शांत ध्यान",
        todaySadhanaSummary = "आज की साधना सारांश",
        totalChantedToday = "आज कुल जप",
        malas = "माला",
        beads = "मनके",
        sankalpaTarget = "संकल्प लक्ष्य",
        streak = "साधना क्रम",
        days = "दिन",
        mantraWiseRecords = "मंत्र-वार आज का विवरण",
        lifetimeTotal = "आजीवन कुल जप",
        lifetimeMalas = "आजीवन माला",
        lifetimeBeads = "आजीवन मनके",
        dhyanTitle = "शांत ध्यान साधना",
        soundDrone = "तानपूरा ओंकार",
        tapAnywherePrompt = "नेत्र बंद कर कहीं भी स्पर्श करें",
        exitDhyan = "ध्यान समाप्त",
        exitTitle = "क्या आप बाहर निकलना चाहते हैं?",
        exitMessage = "आपकी आज की साधना सुरक्षित रूप से सहेजी गई है।",
        todayCompletedMalas = "आज पूर्ण माला",
        continueJap = "जप जारी रखें",
        confirmExit = "बाहर निकलें",
        celebrationTitle = "माला पूर्ण! जय श्री राधे!",
        celebrationDesc = "108 मनकों की पावन माला संपन्न हुई।",
        acceptCelebration = "शुभ साधना जारी रखें",
        settingsTitle = "साधना संकल्प एवं सेटिंग्स",
        settingsSubtitle = "भाषा, दैनिक नियम, ध्वनि, स्पंदन और प्रदर्शन वरीयताएं",
        languageTitle = "भाषा चुनें (Select Language)",
        dailyGoalTitle = "दैनिक संकल्प लक्ष्य (Daily Mala Goal)",
        customGoal = "कस्टम लक्ष्य",
        customMantra = "कस्टम मंत्र दर्ज करें",
        soundFeedback = "जप ध्वनि संकेत",
        vibrationFeedback = "स्पंदन तीव्रता",
        touchHardware = "स्पर्श एवं हार्डवेयर",
        tapAnywhere = "पूरी स्क्रीन पर स्पर्श",
        tapAnywhereDesc = "नेत्र बंद करके ध्यानस्थ जप के लिए",
        volumeKeys = "वॉल्यूम बटन से जप",
        volumeKeysDesc = "स्क्रीन देखे बिना वॉल्यूम बटन दबाकर जपें",
        themeTitle = "थीम (Theme Style)",
        themeGlass = "ग्लासमॉर्फिज्म (Glassmorphism)",
        themeLight = "उषाकाल (Dawn Light)",
        themeDark = "रात्रि ध्यान (Temple Night)",
        themeAuto = "सिस्टम (Auto)",
        devotionalFooter = "श्री राधा रानी के श्री चरण कमलों में समर्पित",
        blessing = "जय जय श्री राधे! शुभ साधना!",
        customMantraDialogTitle = "कस्टम मंत्र",
        enterMantra = "मंत्र लिखें",
        submit = "स्वीकारें",
        cancel = "रद्द करें",
        presetMantras = listOf(
            "श्री राधा",
            "राधे राधे",
            "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
            "श्री राधा कृष्ण",
            "राधा गोविन्द",
            "ॐ नमो भगवते वासुदेवाय"
        ),
        devotionalQuotes = listOf(
            "« जो जन जपहिं सदा राधा नाम, ताहि न व्यापहिं कलिकाल के काम। »",
            "« राधा नाम परम सुखदाई, भज मन मेरे सदा सुखदाई। »",
            "« कोटि कल्प के पाप कटे, मुख निसरै राधा नाम। »",
            "« सकल मनोरथ पूर्ण हों, जपिये श्री राधा नाम। »",
            "« राधा नाम रस पीवै सोई, जेहि पर कृपा किशोरी की होई। »"
        )
    )

    private val bengaliStrings = LanguageStrings(
        appName = "শ্রী রাধা নাম জপ",
        tabJap = "জপ (Jap)",
        tabSadhana = "সাধনা (Sadhana)",
        tabGoal = "সংকল্প (Goal)",
        selectMantra = "মন্ত্র নির্বাচন করুন",
        thisMantraMala = "এই মন্ত্রের মালা",
        bead = "দানা",
        totalToday = "আজ মোট",
        tapToChant = "স্পর্শ করুন (Tap)",
        resetMala = "মালা রিসেট",
        undo = "পূর্বাবস্থায় (Undo)",
        sound = "ধ্বনি",
        dhyanMode = "শান্ত ধ্যান",
        todaySadhanaSummary = "আজকের সাধনা সারাংশ",
        totalChantedToday = "আজকের মোট জপ",
        malas = "মালা",
        beads = "দানা",
        sankalpaTarget = "সংকল্প লক্ষ্য",
        streak = "সাধনা ধারা",
        days = "দিন",
        mantraWiseRecords = "আজকের মন্ত্রভিত্তিক বিবরণ",
        lifetimeTotal = "আজীবন মোট জপ",
        lifetimeMalas = "আজীবন মালা",
        lifetimeBeads = "আজীবন দানা",
        dhyanTitle = "শান্ত ধ্যান সাধনা",
        soundDrone = "তানপুরা ওঙ্কার",
        tapAnywherePrompt = "চোখ বন্ধ করে যে কোনো স্থানে স্পর্শ করুন",
        exitDhyan = "ধ্যান সমাপ্ত",
        exitTitle = "আপনি কি প্রস্থান করতে চান?",
        exitMessage = "আপনার আজকের সাধনা সুরক্ষিতভাবে সংরক্ষিত রয়েছে।",
        todayCompletedMalas = "আজকের পূর্ণ মালা",
        continueJap = "জপ চালিয়ে যান",
        confirmExit = "প্রস্থান করুন",
        celebrationTitle = "মালা পূর্ণ! জয় শ্রী রাধে!",
        celebrationDesc = "১০৮ দানার পবিত্র মালা সম্পন্ন হয়েছে।",
        acceptCelebration = "শুভ সাধনা চালিয়ে যান",
        settingsTitle = "সাধনা সংকল্প ও সেটিংস",
        settingsSubtitle = "ভাষা, দৈনিক নিয়ম, শব্দ, কম্পন ও প্রদর্শন পছন্দ",
        languageTitle = "ভাষা নির্বাচন করুন (Select Language)",
        dailyGoalTitle = "দৈনিক সংকল্প লক্ষ্য (Daily Mala Goal)",
        customGoal = "কাস্টম লক্ষ্য",
        customMantra = "কাস্টম মন্ত্র লিখুন",
        soundFeedback = "জপ ধ্বনি সংকেত",
        vibrationFeedback = "কম্পন তীব্রতা",
        touchHardware = "স্পর্শ ও হার্ডওয়্যার",
        tapAnywhere = "পুরো স্ক্রিনে স্পর্শ",
        tapAnywhereDesc = "চোখ বন্ধ করে ধ্যানমগ্ন জপের জন্য",
        volumeKeys = "ভলিউম বোতামে জপ",
        volumeKeysDesc = "স্ক্রিন না দেখে ভলিউম বোতাম চেপে জপ করুন",
        themeTitle = "থিম (Theme Style)",
        themeGlass = "গ্লাস মরফিজম (Glassmorphism)",
        themeLight = "ঊষাকাল (Dawn Light)",
        themeDark = "রাত্রি ধ্যান (Temple Night)",
        themeAuto = "সিস্টেম (Auto)",
        devotionalFooter = "শ্রী রাধারাণীর শ্রীচরণকমলে সমর্পিত",
        blessing = "জয় জয় শ্রী রাধে! শুভ সাধনা!",
        customMantraDialogTitle = "কাস্টম মন্ত্র",
        enterMantra = "মন্ত্র লিখুন",
        submit = "সংরক্ষণ",
        cancel = "বাতিল",
        presetMantras = listOf(
            "শ্রী রাধা",
            "রাধে রাধে",
            "হরে কৃষ্ণ হরে কৃষ্ণ কৃষ্ণ কৃষ্ণ হরে হরে \nহরে রাম হরে রাম রাম রাম হরে হরে",
            "শ্রী রাধা কৃষ্ণ",
            "রাধা গোবিন্দ",
            "ওঁ নমো ভগবতে বাসুদেবায়"
        ),
        devotionalQuotes = listOf(
            "« যে জন জপয়ে সদা রাধানাম, পাপ তাপ নাশ পায় সর্বকাম। »",
            "« রাধানাম পরম সুখদায়ী, ভজ মন আমার ভবসিন্ধু তরী। »",
            "« কোটি জন্মের পাপ কাটে, মুখে যদি রাধানাম রটে। »"
        )
    )

    private val marathiStrings = LanguageStrings(
        appName = "श्री राधा नाम जप",
        tabJap = "जप (Jap)",
        tabSadhana = "साधना (Sadhana)",
        tabGoal = "संकल्प (Goal)",
        selectMantra = "मंत्र निवडा",
        thisMantraMala = "या मंत्राची माळ",
        bead = "मणी",
        totalToday = "आज एकूण",
        tapToChant = "स्पर्श करा (Tap)",
        resetMala = "माळ रीसेट",
        undo = "मागे घ्या (Undo)",
        sound = "ध्वनी",
        dhyanMode = "शांत ध्यान",
        todaySadhanaSummary = "आजचा साधना सारांश",
        totalChantedToday = "आजचा एकूण जप",
        malas = "माळा",
        beads = "मणी",
        sankalpaTarget = "संकल्प ध्येय",
        streak = "साधना क्रम",
        days = "दिवस",
        mantraWiseRecords = "मंत्रनिहाय आजचा तपशील",
        lifetimeTotal = "आयुष्यभरातील एकूण जप",
        lifetimeMalas = "एकूण माळा",
        lifetimeBeads = "एकूण मणी",
        dhyanTitle = "शांत ध्यान साधना",
        soundDrone = "तानपुरा ओंकार",
        tapAnywherePrompt = "डोळे मिटून कुठेही स्पर्श करा",
        exitDhyan = "ध्यान समाप्त",
        exitTitle = "तुम्हाला बाहेर पडायचे आहे का?",
        exitMessage = "तुमची आजची साधना सुरक्षित जतन केली आहे.",
        todayCompletedMalas = "आज पूर्ण माळा",
        continueJap = "जप सुरू ठेवा",
        confirmExit = "बाहेर पडा",
        celebrationTitle = "माळ पूर्ण! जय श्री राधे!",
        celebrationDesc = "१०८ मण्यांची पावन माळ पूर्ण झाली.",
        acceptCelebration = "साधना सुरू ठेवा",
        settingsTitle = "साधना संकल्प व सेटिंग्ज",
        settingsSubtitle = "भाषा, दैनिक नियम, ध्वनी, कंपन आणि थीम",
        languageTitle = "भाषा निवडा (Select Language)",
        dailyGoalTitle = "दैनिक संकल्प ध्येय (Daily Mala Goal)",
        customGoal = "सानुकूल ध्येय",
        customMantra = "सानुकूल मंत्र टाका",
        soundFeedback = "जप ध्वनी संकेत",
        vibrationFeedback = "कंपन तीव्रता",
        touchHardware = "स्पर्श आणि हार्डवेअर",
        tapAnywhere = "पूर्ण स्क्रीनवर स्पर्श",
        tapAnywhereDesc = "डोळे बंद करून ध्यानस्थ जपासाठी",
        volumeKeys = "व्हॉल्यूम बटणाने जप",
        volumeKeysDesc = "स्क्रीन न बघता व्हॉल्यूम बटणाने जप करा",
        themeTitle = "थीम (Theme Style)",
        themeGlass = "ग्लासमॉर्फिझम (Glassmorphism)",
        themeLight = "उषाकाल (Dawn Light)",
        themeDark = "रात्र ध्यान (Temple Night)",
        themeAuto = "सिस्टम (Auto)",
        devotionalFooter = "श्री राधा राणींच्या चरणकमलात समर्पित",
        blessing = "जय जय श्री राधे! शुभ साधना!",
        customMantraDialogTitle = "सानुकूल मंत्र",
        enterMantra = "मंत्र लिहा",
        submit = "स्वीकारा",
        cancel = "रद्द करा",
        presetMantras = listOf(
            "श्री राधा",
            "राधे राधे",
            "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
            "श्री राधा कृष्ण",
            "राधा गोविंद",
            "ॐ नमो भगवते वासुदेवाय"
        ),
        devotionalQuotes = listOf(
            "« जो जपे सदा राधा नाम, त्याला न बाधे कलीचे काम। »",
            "« राधा नाम परम सुखदायी, स्मरा मना सदा सुखदायी। »"
        )
    )

    private val teluguStrings = LanguageStrings(
        appName = "శ్రీ రాధా నామ జపం",
        tabJap = "జపం (Jap)",
        tabSadhana = "సాధన (Sadhana)",
        tabGoal = "సంకల్పం (Goal)",
        selectMantra = "మంత్రాన్ని ఎంచుకోండి",
        thisMantraMala = "ఈ మంత్రం మాల",
        bead = "పూస",
        totalToday = "నేటి మొత్తం",
        tapToChant = "స్పర్శించండి (Tap)",
        resetMala = "మాల రీసెట్",
        undo = "వెనక్కి (Undo)",
        sound = "ధ్వని",
        dhyanMode = "శాంత ధ్యానం",
        todaySadhanaSummary = "నేటి సాధన సారాంశం",
        totalChantedToday = "నేటి మొత్తం జపం",
        malas = "మాలలు",
        beads = "పూసలు",
        sankalpaTarget = "సంకల్ప లక్ష్యం",
        streak = "సాధన క్రమం",
        days = "రోజులు",
        mantraWiseRecords = "నేటి మంత్రాల వివరాలు",
        lifetimeTotal = "జీవితకాల మొత్తం జపం",
        lifetimeMalas = "మొత్తం మాలలు",
        lifetimeBeads = "మొత్తం పూసలు",
        dhyanTitle = "శాంత ధ్యాన సాధన",
        soundDrone = "తాంబూరా ఓంకారం",
        tapAnywherePrompt = "కళ్ళు మూసుకుని ఎక్కడైనా తాకండి",
        exitDhyan = "ధ్యానం ముగించు",
        exitTitle = "మీరు నిష్క్రమించాలనుకుంటున్నారా?",
        exitMessage = "మీ నేటి సాధన సురక్షితంగా భద్రపరచబడింది.",
        todayCompletedMalas = "నేడు పూర్తయిన మాలలు",
        continueJap = "జపం కొనసాగించండి",
        confirmExit = "నిష్క్రమించు",
        celebrationTitle = "మాల పూర్తయింది! జై శ్రీ రాధే!",
        celebrationDesc = "108 పూసల పవిత్ర మాల పూర్తయింది.",
        acceptCelebration = "సాధన కొనసాగించండి",
        settingsTitle = "సాధనా సంకల్పం & సెట్టింగ్‌లు",
        settingsSubtitle = "భాష, రోజువారీ నియమాలు, శబ్దం, కంపనం",
        languageTitle = "భాషను ఎంచుకోండి (Select Language)",
        dailyGoalTitle = "రోజువారీ సంకల్ప లక్ష్యం (Daily Mala Goal)",
        customGoal = "అనుకూల లక్ష్యం",
        customMantra = "కస్టమ్ మంత్రం నమోదు చేయండి",
        soundFeedback = "జప శబ్ద సంకేతం",
        vibrationFeedback = "కంపన తీవ్రత",
        touchHardware = "టచ్ & హార్డ్‌వేర్",
        tapAnywhere = "పూర్తి స్క్రీన్‌పై స్పర్శ",
        tapAnywhereDesc = "కళ్ళు మూసుకుని ధ్యానంలో జపించడానికి",
        volumeKeys = "వాల్యూమ్ బటన్లతో జపం",
        volumeKeysDesc = "స్క్రీన్ చూడకుండా వాల్యూమ్ కీలతో జపించండి",
        themeTitle = "థీమ్ (Theme Style)",
        themeGlass = "గ్లాస్‌మార్ఫిజం (Glassmorphism)",
        themeLight = "ఉదయ కాంతి (Dawn Light)",
        themeDark = "రాత్రి ధ్యానం (Temple Night)",
        themeAuto = "సిస్టమ్ (Auto)",
        devotionalFooter = "శ్రీ రాధారాణి చరణారవిందములకు సమర్పితం",
        blessing = "జై జై శ్రీ రాధే! శుభ సాధన!",
        customMantraDialogTitle = "కస్టమ్ మంత్రం",
        enterMantra = "మంత్రం వ్రాయండి",
        submit = "సమర్పించు",
        cancel = "రద్దు చేయి",
        presetMantras = listOf(
            "శ్రీ రాధా",
            "రాధే రాధే",
            "హరే కృష్ణ హరే కృష్ణ కృష్ణ కృష్ణ హరే హరే \nహరే రామ హరే రామ రామ రామ హరే హరే",
            "శ్రీ రాధా కృష్ణ",
            "రాధా గోవింద",
            "ఓం నమో భగవతే వాసుదేవాయ"
        ),
        devotionalQuotes = listOf(
            "« ఎవరైతే సదా రాధా నామమును జపిస్తారో వారికి సర్వ సంపదలు కలుగుతాయి. »",
            "« రాధా నామము పరమ సుఖదాయకం, జపించండి సదా. »"
        )
    )

    private val tamilStrings = LanguageStrings(
        appName = "ஸ்ரீ ராதா நாம ஜபம்",
        tabJap = "ஜபம் (Jap)",
        tabSadhana = "சாதனை (Sadhana)",
        tabGoal = "சங்கல்பம் (Goal)",
        selectMantra = "மந்திரத்தைத் தேர்ந்தெடுக்கவும்",
        thisMantraMala = "இந்த மந்திர மாலை",
        bead = "மணி",
        totalToday = "இன்று மொத்தம்",
        tapToChant = "தொட்டு ஜபிக்கவும் (Tap)",
        resetMala = "மாலை மீட்டமை",
        undo = "முந்தையது (Undo)",
        sound = "ஒலி",
        dhyanMode = "தியான முறை",
        todaySadhanaSummary = "இன்றைய சாதனை சுருக்கம்",
        totalChantedToday = "இன்றைய மொத்த ஜபம்",
        malas = "மாலைகள்",
        beads = "மணிகள்",
        sankalpaTarget = "சங்கல்ப இலக்கு",
        streak = "தொடர் சாதனை",
        days = "நாட்கள்",
        mantraWiseRecords = "இன்றைய மந்திர விவரங்கள்",
        lifetimeTotal = "வாழ்நாள் மொத்த ஜபம்",
        lifetimeMalas = "மொத்த மாலைகள்",
        lifetimeBeads = "மொத்த மணிகள்",
        dhyanTitle = "அமைதியான தியான சாதனை",
        soundDrone = "தம்பூரா ஓங்காரம்",
        tapAnywherePrompt = "கண்களை மூடி எங்கு வேண்டுமானாலும் தொடவும்",
        exitDhyan = "தியானம் முடிந்தது",
        exitTitle = "வெளியேற விரும்புகிறீர்களா?",
        exitMessage = "உங்கள் இன்றைய சாதனை பாதுகாப்பாக சேமிக்கப்பட்டுள்ளது.",
        todayCompletedMalas = "இன்று முடிந்த மாலைகள்",
        continueJap = "ஜபத்தைத் தொடரவும்",
        confirmExit = "வெளியேறு",
        celebrationTitle = "மாலை நிறைவு! ஜெய் ஸ்ரீ ராதே!",
        celebrationDesc = "108 மணிகள் கொண்ட புனித மாலை நிறைவடைந்தது.",
        acceptCelebration = "சாதனையைத் தொடரவும்",
        settingsTitle = "சாதனை சங்கல்பம் & அமைப்புகள்",
        settingsSubtitle = "மொழி, தினசரி விதிகள், ஒலி, அதிர்வு",
        languageTitle = "மொழியைத் தேர்ந்தெடுக்கவும் (Select Language)",
        dailyGoalTitle = "தினசரி சங்கல்ப இலக்கு (Daily Mala Goal)",
        customGoal = "தனிப்பயன் இலக்கு",
        customMantra = "தனிப்பயன் மந்திரம்",
        soundFeedback = "ஜப ஒலி சமிக்ஞை",
        vibrationFeedback = "அதிர்வு தீவிரம்",
        touchHardware = "தொடுதல் & வன்பொருள்",
        tapAnywhere = "முழு திரையிலும் தொடுதல்",
        tapAnywhereDesc = "கண்களை மூடி தியானிக்க",
        volumeKeys = "வால்யூம் பட்டன் ஜபம்",
        volumeKeysDesc = "திரையைப் பார்க்காமல் ஜபிக்க",
        themeTitle = "வடிவமைப்பு (Theme Style)",
        themeGlass = "கிளாஸ்மார்ஃபிசம் (Glassmorphism)",
        themeLight = "விடியல் ஒளி (Dawn Light)",
        themeDark = "இரவு தியானம் (Temple Night)",
        themeAuto = "தானியங்கு (Auto)",
        devotionalFooter = "ஸ்ரீ ராதாராணியின் திருவடிகளில் சமர்ப்பணம்",
        blessing = "ஜெய் ஜெய் ஸ்ரீ ராதே! சுப சாதனை!",
        customMantraDialogTitle = "தனிப்பயன் மந்திரம்",
        enterMantra = "மந்திரத்தை உள்ளிடவும்",
        submit = "ஏற்றுக்கொள்",
        cancel = "ரத்து செய்",
        presetMantras = listOf(
            "ஸ்ரீ ராதா",
            "ராதே ராதே",
            "ஹரே கிருஷ்ண ஹரே கிருஷ்ண கிருஷ்ண கிருஷ்ண ஹரே ஹரே \nஹரே ராம ஹரே ராம ராம ராம ஹரே ராம",
            "ஸ்ரீ ராதா கிருஷ்ண",
            "ராதா கோவிந்த",
            "ஓம் நமோ பகவதே வாஸுதேவாய"
        ),
        devotionalQuotes = listOf(
            "« எவர் எப்போதும் ராதா நாமம் ஜபிக்கிறாரோ அவருக்கு நன்மைகள் உண்டாகும். »",
            "« ராதா நாமம் பரம சுகமளிப்பது. »"
        )
    )

    private val gujaratiStrings = LanguageStrings(
        appName = "શ્રી રાધા નામ જપ",
        tabJap = "જપ (Jap)",
        tabSadhana = "સાધના (Sadhana)",
        tabGoal = "સંકલ્પ (Goal)",
        selectMantra = "મંત્ર પસંદ કરો",
        thisMantraMala = "આ મંત્રની માળા",
        bead = "મણકો",
        totalToday = "આજે કુલ",
        tapToChant = "સ્પર્શ કરો (Tap)",
        resetMala = "માળા રીસેટ",
        undo = "પાછું લો (Undo)",
        sound = "ધ્વનિ",
        dhyanMode = "શાંત ધ્યાન",
        todaySadhanaSummary = "આજનો સાધના સારાંશ",
        totalChantedToday = "આજનો કુલ જપ",
        malas = "માળા",
        beads = "મણકા",
        sankalpaTarget = "સંકલ્પ લક્ષ્ય",
        streak = "સાધના ક્રમ",
        days = "દિવસ",
        mantraWiseRecords = "મંત્ર મુજબ આજની વિગત",
        lifetimeTotal = "આજીવન કુલ જપ",
        lifetimeMalas = "કુલ માળા",
        lifetimeBeads = "કુલ મણકા",
        dhyanTitle = "શાંત ધ્યાન સાધના",
        soundDrone = "તાનપુરા ઓમકાર",
        tapAnywherePrompt = "આંખો બંધ કરી ગમે ત્યાં સ્પર્શ કરો",
        exitDhyan = "ધ્યાન સમાપ્ત",
        exitTitle = "શું તમે બહાર નીકળવા માંગો છો?",
        exitMessage = "તમારી આજની સાધના સુરક્ષિત રીતે સચવાયેલ છે.",
        todayCompletedMalas = "આજે પૂર્ણ માળા",
        continueJap = "જપ ચાલુ રાખો",
        confirmExit = "બહાર નીકળો",
        celebrationTitle = "માળા પૂર્ણ! જય શ્રી રાધે!",
        celebrationDesc = "૧૦૮ મણકાની પવિત્ર માળા સંપન્ન થઈ.",
        acceptCelebration = "સાધના ચાલુ રાખો",
        settingsTitle = "સાધના સંકલ્પ અને સેટિંગ્સ",
        settingsSubtitle = "ભાષા, દૈનિક નિયમ, અવાજ, કંપન અને થીમ",
        languageTitle = "ભાષા પસંદ કરો (Select Language)",
        dailyGoalTitle = "દૈનિક સંકલ્પ લક્ષ્ય (Daily Mala Goal)",
        customGoal = "કસ્ટમ લક્ષ્ય",
        customMantra = "કસ્ટમ મંત્ર ઉમેરો",
        soundFeedback = "જપ ધ્વનિ સંકેત",
        vibrationFeedback = "કંપન તીવ્રતા",
        touchHardware = "સ્પર્શ અને હાર્ડવેર",
        tapAnywhere = "આખી સ્ક્રીન પર સ્પર્શ",
        tapAnywhereDesc = "આંખો બંધ રાખી ધ્યાનમાં જપવા માટે",
        volumeKeys = "વોલ્યુમ બટનથી જપ",
        volumeKeysDesc = "સ્ક્રીન જોયા વિના વોલ્યુમ બટન દબાવીને જપો",
        themeTitle = "થીમ (Theme Style)",
        themeGlass = "ગ્લાસ મોર્ફિઝમ (Glassmorphism)",
        themeLight = "પ્રભાત પ્રકાશ (Dawn Light)",
        themeDark = "રાત્રિ ધ્યાન (Temple Night)",
        themeAuto = "સિસ્ટમ (Auto)",
        devotionalFooter = "શ્રી રાધારાણીના ચરણકમળમાં સમર્પિત",
        blessing = "જય જય શ્રી રાધે! શુભ સાધના!",
        customMantraDialogTitle = "કસ્ટમ મંત્ર",
        enterMantra = "મંત્ર લખો",
        submit = "સ્વીકારો",
        cancel = "રદ કરો",
        presetMantras = listOf(
            "શ્રી રાધા",
            "રાધે રાધે",
            "હરે કૃષ્ણ હરે કૃષ્ણ કૃષ્ણ કૃષ્ણ હરે હરે \nહરે રામ હરે રામ રામ રામ હરે હરે",
            "શ્રી રાધા કૃષ્ણ",
            "રાધા ગોવિંદ",
            "ૐ નમો ભગવતે વાસુદેવાય"
        ),
        devotionalQuotes = listOf(
            "« જે જન જપે સદા રાધા નામ, તેના સરે સૌ કાજ તમામ. »",
            "« રાધા નામ પરમ સુખદાયી, ભજો મન સદા સુખદાયી. »"
        )
    )

    private val kannadaStrings = LanguageStrings(
        appName = "ಶ್ರೀ ರಾಧಾ ನಾಮ ಜಪ",
        tabJap = "ಜಪ (Jap)",
        tabSadhana = "ಸಾಧನೆ (Sadhana)",
        tabGoal = "ಸಂಕಲ್ಪ (Goal)",
        selectMantra = "ಮಂತ್ರವನ್ನು ಆಯ್ಕೆಮಾಡಿ",
        thisMantraMala = "ಈ ಮಂತ್ರದ ಮಾಲೆ",
        bead = "ಮಣಿ",
        totalToday = "ಇಂದು ಒಟ್ಟು",
        tapToChant = "ಸ್ಪರ್ಶಿಸಿ (Tap)",
        resetMala = "ಮಾಲೆ ಮರುಹೊಂದಿಸಿ",
        undo = "ಹಿಂದಕ್ಕೆ (Undo)",
        sound = "ಧ್ವನಿ",
        dhyanMode = "ಶಾಂತ ಧ್ಯಾನ",
        todaySadhanaSummary = "ಇಂದಿನ ಸಾಧನಾ ಸಾರಾಂಶ",
        totalChantedToday = "ಇಂದಿನ ಒಟ್ಟು ಜಪ",
        malas = "ಮಾಲೆಗಳು",
        beads = "ಮಣಿಗಳು",
        sankalpaTarget = "ಸಂಕಲ್ಪ ಗುರಿ",
        streak = "ಸಾಧನಾ ಕ್ರಮ",
        days = "ದಿನಗಳು",
        mantraWiseRecords = "ಇಂದಿನ ಮಂತ್ರವಾರು ವಿವರಗಳು",
        lifetimeTotal = "ಜೀವಿತಾವಧಿಯ ಒಟ್ಟು ಜಪ",
        lifetimeMalas = "ಒಟ್ಟು ಮಾಲೆಗಳು",
        lifetimeBeads = "ಒಟ್ಟು ಮಣಿಗಳು",
        dhyanTitle = "ಶಾಂತ ಧ್ಯಾನ ಸಾಧನೆ",
        soundDrone = "ತಂಬೂರಿ ಓಂಕಾರ",
        tapAnywherePrompt = "ಕಣ್ಣು ಮುಚ್ಚಿ ಎಲ್ಲಿಯಾದರೂ ಸ್ಪರ್ಶಿಸಿ",
        exitDhyan = "ಧ್ಯಾನ ಮುಕ್ತಾಯ",
        exitTitle = "ನೀವು ನಿರ್ಗಮಿಸಲು ಬಯಸುವಿರಾ?",
        exitMessage = "ನಿಮ್ಮ ಇಂದಿನ ಸಾಧನೆ ಸುರಕ್ಷಿತವಾಗಿ ಸಂಗ್ರಹಿಸಲಾಗಿದೆ.",
        todayCompletedMalas = "ಇಂದು ಪೂರ್ಣಗೊಂಡ ಮಾಲೆಗಳು",
        continueJap = "ಜಪ ಮುಂದುವರಿಸಿ",
        confirmExit = "ನಿರ್ಗಮಿಸಿ",
        celebrationTitle = "ಮಾಲೆ ಪೂರ್ಣ! ಜೈ ಶ್ರೀ ರಾಧೇ!",
        celebrationDesc = "108 ಮಣಿಗಳ ಪವಿತ್ರ ಮಾಲೆ ಪೂರ್ಣಗೊಂಡಿದೆ.",
        acceptCelebration = "ಸಾಧನೆ ಮುಂದುವರಿಸಿ",
        settingsTitle = "ಸಾಧನಾ ಸಂಕಲ್ಪ & ಸೆಟ್ಟಿಂಗ್‌ಗಳು",
        settingsSubtitle = "ಭಾಷೆ, ದೈನಂದಿನ ನಿಯಮಗಳು, ಧ್ವನಿ, ಕಂಪನ",
        languageTitle = "ಭಾಷೆಯನ್ನು ಆಯ್ಕೆಮಾಡಿ (Select Language)",
        dailyGoalTitle = "ದೈನಂದಿನ ಸಂಕಲ್ಪ ಗುರಿ (Daily Mala Goal)",
        customGoal = "ಕಸ್ಟಮ್ ಗುರಿ",
        customMantra = "ಕಸ್ಟಮ್ ಮಂತ್ರ ನಮೂದಿಸಿ",
        soundFeedback = "ಜಪ ಧ್ವನಿ ಸಂಕೇತ",
        vibrationFeedback = "ಕಂಪನ ತೀವ್ರತೆ",
        touchHardware = "ಸ್ಪರ್ಶ ಮತ್ತು ಹಾರ್ಡ್‌ವೇರ್",
        tapAnywhere = "ಪೂರ್ಣ ಪರದೆಯ ಸ್ಪರ್ಶ",
        tapAnywhereDesc = "ಕಣ್ಣು ಮುಚ್ಚಿ ಧ್ಯಾನಸ್ಥ ಜಪಕ್ಕಾಗಿ",
        volumeKeys = "ವಾಲ್ಯೂಮ್ ಬಟನ್ ಜಪ",
        volumeKeysDesc = "ಪರದೆ ನೋಡದೆ ವಾಲ್ಯೂಮ್ ಬಟನ್ ಒತ್ತಿ ಜಪಿಸಿ",
        themeTitle = "ಥೀಮ್ (Theme Style)",
        themeGlass = "ಗ್ಲಾಸ್‌ಮಾರ್ಫಿಸಂ (Glassmorphism)",
        themeLight = "ಉಷಃಕಾಲದ ಬೆಳಕು (Dawn Light)",
        themeDark = "ರಾತ್ರಿ ಧ್ಯಾನ (Temple Night)",
        themeAuto = "ಆಟೋ (Auto)",
        devotionalFooter = "ಶ್ರೀ ರಾಧಾರಾಣಿಯವರ ಶ್ರೀಚರಣಕಮಲಗಳಿಗೆ ಸಮರ್ಪಿತ",
        blessing = "ಜೈ ಜೈ ಶ್ರೀ ರಾಧೇ! ಶುಭ ಸಾಧನೆ!",
        customMantraDialogTitle = "ಕಸ್ಟಮ್ ಮಂತ್ರ",
        enterMantra = "ಮಂತ್ರ ಬರೆಯಿರಿ",
        submit = "ಸ್ವೀಕರಿಸಿ",
        cancel = "ರದ್ದುಮಾಡಿ",
        presetMantras = listOf(
            "ಶ್ರೀ ರಾಧಾ",
            "ರಾಧೇ ರಾಧೇ",
            "ಹರೇ ಕೃಷ್ಣ ಹರೇ ಕೃಷ್ಣ ಕೃಷ್ಣ ಕೃಷ್ಣ ಹರೇ ಹರೇ \nಹರೇ ರಾಮ ಹರೇ ರಾಮ ರಾಮ ರಾಮ ಹರೇ ಹರೇ",
            "ಶ್ರೀ ರಾಧಾ ಕೃಷ್ಣ",
            "ರಾಧಾ ಗೋವಿಂದ",
            "ಓಂ ನಮೋ ಭಗವತೇ ವಾಸುದೇವಾಯ"
        ),
        devotionalQuotes = listOf(
            "« ಯಾರು ಸದಾ ರಾಧಾ ನಾಮವನ್ನು ಜಪಿಸುತ್ತಾರೋ ಅವರಿಗೆ ಸಕಲ ಸುಖ ಸಿಗುತ್ತದೆ. »",
            "« ರಾಧಾ ನಾಮವು ಪರಮ ಸುಖದಾಯಕ. »"
        )
    )

    private val malayalamStrings = LanguageStrings(
        appName = "ശ്രീ രാധാ നാമ ജപം",
        tabJap = "ജപം (Jap)",
        tabSadhana = "സാധന (Sadhana)",
        tabGoal = "സങ്കൽപം (Goal)",
        selectMantra = "മന്ത്രം തിരഞ്ഞെടുക്കുക",
        thisMantraMala = "ഈ മന്ത്ര മാല",
        bead = "മണി",
        totalToday = "ഇന്ന് ആകെ",
        tapToChant = "സ്പർശിക്കുക (Tap)",
        resetMala = "മാല പുനഃക്രമീകരിക്കുക",
        undo = "തിരിച്ചെടുക്കുക (Undo)",
        sound = "ശബ്ദം",
        dhyanMode = "ശാന്ത ധ്യാനം",
        todaySadhanaSummary = "ഇന്നത്തെ സാധനാ സംഗ്രഹം",
        totalChantedToday = "ഇന്നത്തെ ആകെ ജപം",
        malas = "മാലകൾ",
        beads = "മണികൾ",
        sankalpaTarget = "സങ്കൽപ ലക്ഷ്യം",
        streak = "സാധനാ ക്രമം",
        days = "ദിവസങ്ങൾ",
        mantraWiseRecords = "ഇന്നത്തെ മന്ത്ര വിവരങ്ങൾ",
        lifetimeTotal = "ആജീവനാന്ത ആകെ ജപം",
        lifetimeMalas = "ആകെ മാലകൾ",
        lifetimeBeads = "ആകെ മണികൾ",
        dhyanTitle = "ശാന്ത ധ്യാന സാധന",
        soundDrone = "തമ്പുരു ഓംകാരം",
        tapAnywherePrompt = "കണ്ണുകൾ അടച്ച് എവിടെയും സ്പർശിക്കുക",
        exitDhyan = "ധ്യാനം പൂർത്തിയായി",
        exitTitle = "പുറത്തുകടക്കാൻ ആഗ്രഹിക്കുന്നുണ്ടോ?",
        exitMessage = "നിങ്ങളുടെ ഇന്നത്തെ സാധന സുരക്ഷിതമായി സൂക്ഷിച്ചിരിക്കുന്നു.",
        todayCompletedMalas = "ഇന്ന് പൂർത്തിയായ മാലകൾ",
        continueJap = "ജപം തുടരുക",
        confirmExit = "പുറത്തുകടക്കുക",
        celebrationTitle = "മാല പൂർത്തിയായി! ജയ് ശ്രീ രാധേ!",
        celebrationDesc = "108 മണികളുടെ പവിത്ര മാല പൂർത്തിയായി.",
        acceptCelebration = "സാധന തുടരുക",
        settingsTitle = "സാധനാ സങ്കൽപവും ക്രമീകരണങ്ങളും",
        settingsSubtitle = "ഭാഷ, നിയമങ്ങൾ, ശബ്ദം, കമ്പനം",
        languageTitle = "ഭാഷ തിരഞ്ഞെടുക്കുക (Select Language)",
        dailyGoalTitle = "പ്രതിദിന സങ്കൽപ ലക്ഷ്യം (Daily Mala Goal)",
        customGoal = "ഇഷ്‌ടാനുസൃത ലക്ഷ്യം",
        customMantra = "ഇഷ്‌ടാനുസൃത മന്ത്രം നൽകുക",
        soundFeedback = "ജപ ശബ്ദ സൂചന",
        vibrationFeedback = "കമ്പന തീവ്രത",
        touchHardware = "സ്പർശനവും ഹാർഡ്‌വെയറും",
        tapAnywhere = "സ്‌ക്രീനിൽ എവിടെയും സ്പർശിക്കാം",
        tapAnywhereDesc = "കണ്ണുകൾ അടച്ച് ധ്യാനിക്കാൻ",
        volumeKeys = "വോളിയം ബട്ടൺ ജപം",
        volumeKeysDesc = "സ്‌ക്രീൻ നോക്കാതെ ജപിക്കാൻ",
        themeTitle = "തീം (Theme Style)",
        themeGlass = "ഗ്ലാസ് മോർഫിസം (Glassmorphism)",
        themeLight = "പ്രഭാത വെളിച്ചം (Dawn Light)",
        themeDark = "രാത്രി ധ്യാനം (Temple Night)",
        themeAuto = "ഓട്ടോ (Auto)",
        devotionalFooter = "ശ്രീ രാധാറാണിയുടെ പാദാരവിന്ദങ്ങളിൽ സമർപ്പിതം",
        blessing = "ജയ് ജയ് ശ്രീ രാധേ! ശുഭ സാധന!",
        customMantraDialogTitle = "ഇഷ്‌ടാനുസൃത മന്ത്രം",
        enterMantra = "മന്ത്രം എഴുതുക",
        submit = "സ്ഥിരീകരിക്കുക",
        cancel = "റദ്ദാക്കുക",
        presetMantras = listOf(
            "ശ്രീ രാധാ",
            "രാധേ രാധേ",
            "ഹരേ കൃഷ്ണ ഹരേ കൃഷ്ണ കൃഷ്ണ കൃഷ്ണ ഹരേ ഹരേ \nഹരേ രാമ ഹരേ രാമ രാമ രാമ ഹരേ ഹരേ",
            "ശ്രീ രാധാ കൃഷ്ണ",
            "രാധാ ഗോവിന്ദ",
            "ഓം നമോ ഭഗവതേ വാസുദേവായ"
        ),
        devotionalQuotes = listOf(
            "« രാധാ നാമം സദാ ജപിക്കുന്നവർക്ക് ശാന്തി ലഭിക്കും. »",
            "« രാധാ നാമം പരമ സുഖദായകമാണ്. »"
        )
    )

    private val odiaStrings = LanguageStrings(
        appName = "ଶ୍ରୀ ରାଧା ନାମ ଜପ",
        tabJap = "ଜପ (Jap)",
        tabSadhana = "ସାଧନା (Sadhana)",
        tabGoal = "ସଂକଳ୍ପ (Goal)",
        selectMantra = "ମନ୍ତ୍ର ବାଛନ୍ତୁ",
        thisMantraMala = "ଏହି ମନ୍ତ୍ରର ମାଳା",
        bead = "ଦାନା",
        totalToday = "ଆଜି ସମୁଦାୟ",
        tapToChant = "ସ୍ପର୍ଶ କରନ୍ତୁ (Tap)",
        resetMala = "ମାଳା ରିସେଟ୍",
        undo = "ପୂର୍ବାବସ୍ଥା (Undo)",
        sound = "ଧ୍ୱନି",
        dhyanMode = "ଶାନ୍ତ ଧ୍ୟାନ",
        todaySadhanaSummary = "ଆଜିର ସାଧନା ସାରାଂଶ",
        totalChantedToday = "ଆଜିର ସମୁଦାୟ ଜପ",
        malas = "ମାଳା",
        beads = "ଦାନା",
        sankalpaTarget = "ସଂକଳ୍ପ ଲକ୍ଷ୍ୟ",
        streak = "ସାଧନା କ୍ରମ",
        days = "ଦିନ",
        mantraWiseRecords = "ମନ୍ତ୍ର ଅନୁଯାୟୀ ଆଜିର ବିବରଣୀ",
        lifetimeTotal = "ଆଜୀବନ ସମୁଦାୟ ଜପ",
        lifetimeMalas = "ସମୁଦାୟ ମାଳା",
        lifetimeBeads = "ସମୁଦାୟ ଦାନା",
        dhyanTitle = "ଶାନ୍ତ ଧ୍ୟାନ ସାଧନା",
        soundDrone = "ତାନପୁରା ଓଁକାର",
        tapAnywherePrompt = "ଆଖି ବନ୍ଦ କରି ଯେକୌଣସି ସ୍ଥାନରେ ସ୍ପର୍ଶ କରନ୍ତୁ",
        exitDhyan = "ଧ୍ୟାନ ସମାପ୍ତ",
        exitTitle = "ଆପଣ ପ୍ରସ୍ଥାନ କରିବାକୁ ଚାହାଁନ୍ତି କି?",
        exitMessage = "ଆପଣଙ୍କ ଆଜିର ସାଧନା ସୁରକ୍ଷିତ ଭାବରେ ସଂରକ୍ଷିତ ହୋଇଛି।",
        todayCompletedMalas = "ଆଜି ସମ୍ପୂର୍ଣ୍ଣ ମାଳା",
        continueJap = "ଜପ ଜାରି ରଖନ୍ତୁ",
        confirmExit = "ପ୍ରସ୍ଥାନ କରନ୍ତୁ",
        celebrationTitle = "ମାଳା ସମ୍ପୂର୍ଣ୍ଣ! ଜୟ ଶ୍ରୀ ରାଧେ!",
        celebrationDesc = "୧୦୮ ଦାନାର ପବିତ୍ର ମାଳା ସମ୍ପନ୍ନ ହେଲା।",
        acceptCelebration = "ସାଧନା ଜାରି ରଖନ୍ତୁ",
        settingsTitle = "ସାଧନା ସଂକଳ୍ପ ଏବଂ ସେଟିଂସ",
        settingsSubtitle = "ଭାଷା, ନିୟମ, ଶବ୍ଦ, କମ୍ପନ ଏବଂ ଥିମ୍",
        languageTitle = "ଭାଷା ଚୟନ କରନ୍ତୁ (Select Language)",
        dailyGoalTitle = "ଦୈନିକ ସଂକଳ୍ପ ଲକ୍ଷ୍ୟ (Daily Mala Goal)",
        customGoal = "କଷ୍ଟମ୍ ଲକ୍ଷ୍ୟ",
        customMantra = "କଷ୍ଟମ୍ ମନ୍ତ୍ର ଲେଖନ୍ତୁ",
        soundFeedback = "ଜପ ଧ୍ୱନି ସଙ୍କେତ",
        vibrationFeedback = "କମ୍ପନ ତୀବ୍ରତା",
        touchHardware = "ସ୍ପର୍ଶ ଏବଂ ହାର୍ଡୱେର୍",
        tapAnywhere = "ସମ୍ପୂର୍ଣ୍ଣ ସ୍କ୍ରିନରେ ସ୍ପର୍ଶ",
        tapAnywhereDesc = "ଆଖି ବନ୍ଦ କରି ଧ୍ୟାନରେ ଜପିବା ପାଇଁ",
        volumeKeys = "ଭଲ୍ୟୁମ୍ ବଟନରେ ଜପ",
        volumeKeysDesc = "ସ୍କ୍ରିନ୍ ନଦେଖି ଭଲ୍ୟୁମ୍ କୀ ସାହାଯ୍ୟରେ ଜପନ୍ତୁ",
        themeTitle = "ଥିମ୍ (Theme Style)",
        themeGlass = "ଗ୍ଲାସ୍ ମର୍ଫିଜିମ୍ (Glassmorphism)",
        themeLight = "ଉଷାକାଳ (Dawn Light)",
        themeDark = "ରାତ୍ରି ଧ୍ୟାନ (Temple Night)",
        themeAuto = "ସ୍ୱୟଂଚାଳିତ (Auto)",
        devotionalFooter = "ଶ୍ରୀ ରାଧାରାଣୀଙ୍କ ଶ୍ରୀଚରଣ କମଳରେ ସମର୍ପିତ",
        blessing = "ଜୟ ଜୟ ଶ୍ରୀ ରାଧେ! ଶୁଭ ସାଧନା!",
        customMantraDialogTitle = "କଷ୍ଟମ୍ ମନ୍ତ୍ର",
        enterMantra = "ମନ୍ତ୍ର ଲେଖନ୍ତୁ",
        submit = "ଗ୍ରହଣ କରନ୍ତୁ",
        cancel = "ବାତିଲ କରନ୍ତୁ",
        presetMantras = listOf(
            "ଶ୍ରୀ ରାଧା",
            "ରାଧେ ରାଧେ",
            "ହରେ କୃଷ୍ଣ ହରେ କୃଷ୍ଣ କୃଷ୍ଣ କୃଷ୍ଣ ହରେ ହରେ \nହରେ ରାମ ହରେ ରାମ ରାମ ରାମ ହରେ ହରେ",
            "ଶ୍ରୀ ରାଧା କୃଷ୍ଣ",
            "ରାଧା ଗୋବିନ୍ଦ",
            "ଓଁ ନମୋ ଭଗବତେ ବାସୁଦେବାୟ"
        ),
        devotionalQuotes = listOf(
            "« ଯେଉଁ ଜନ ଜପେ ସଦା ରାଧା ନାମ, ତାହାର ପୂରଣ ହୁଏ ସବୁ କାମ। »",
            "« ରାଧା ନାମ ପରମ ସୁଖଦାୟୀ। »"
        )
    )

    private val punjabiStrings = LanguageStrings(
        appName = "ਸ਼੍ਰੀ ਰਾਧਾ ਨਾਮ ਜਪ",
        tabJap = "ਜਪ (Jap)",
        tabSadhana = "ਸਾਧਨਾ (Sadhana)",
        tabGoal = "ਸੰਕਲਪ (Goal)",
        selectMantra = "ਮੰਤਰ ਚੁਣੋ",
        thisMantraMala = "ਇਸ ਮੰਤਰ ਦੀ ਮਾਲਾ",
        bead = "ਮਣਕਾ",
        totalToday = "ਅੱਜ ਕੁੱਲ",
        tapToChant = "ਛੋਹਵੋ (Tap)",
        resetMala = "ਮਾਲਾ ਰੀਸੈੱਟ",
        undo = "ਵਾਪਸ (Undo)",
        sound = "ਆਵਾਜ਼",
        dhyanMode = "ਸ਼ਾਂਤ ਧਿਆਨ",
        todaySadhanaSummary = "ਅੱਜ ਦਾ ਸਾਧਨਾ ਸਾਰ",
        totalChantedToday = "ਅੱਜ ਕੁੱਲ ਜਪ",
        malas = "ਮਾਲਾਵਾਂ",
        beads = "ਮਣਕੇ",
        sankalpaTarget = "ਸੰਕਲਪ ਟੀਚਾ",
        streak = "ਸਾਧਨਾ ਲੜੀ",
        days = "ਦਿਨ",
        mantraWiseRecords = "ਮੰਤਰ ਅਨੁਸਾਰ ਅੱਜ ਦਾ ਵੇਰਵਾ",
        lifetimeTotal = "ਜੀਵਨ ਭਰ ਦਾ ਕੁੱਲ ਜਪ",
        lifetimeMalas = "ਕੁੱਲ ਮਾਲਾਵਾਂ",
        lifetimeBeads = "ਕੁੱਲ ਮਣਕੇ",
        dhyanTitle = "ਸ਼ਾਂਤ ਧਿਆਨ ਸਾਧਨਾ",
        soundDrone = "ਤਾਨਪੁਰਾ ਓਅੰਕਾਰ",
        tapAnywherePrompt = "ਅੱਖਾਂ ਬੰਦ ਕਰਕੇ ਕਿਤੇ ਵੀ ਛੋਹਵੋ",
        exitDhyan = "ਧਿਆਨ ਸਮਾਪਤ",
        exitTitle = "ਕੀ ਤੁਸੀਂ ਬਾਹਰ ਜਾਣਾ ਚਾਹੁੰਦੇ ਹੋ?",
        exitMessage = "ਤੁਹਾਡੀ ਅੱਜ ਦੀ ਸਾਧਨਾ ਸੁਰੱਖਿਅਤ ਦਰਜ ਹੈ।",
        todayCompletedMalas = "ਅੱਜ ਪੂਰੀਆਂ ਹੋਈਆਂ ਮਾਲਾਵਾਂ",
        continueJap = "ਜਪ ਜਾਰੀ ਰੱਖੋ",
        confirmExit = "ਬਾਹਰ ਜਾਓ",
        celebrationTitle = "ਮਾਲਾ ਪੂਰੀ! ਜੈ ਸ਼੍ਰੀ ਰਾਧੇ!",
        celebrationDesc = "108 ਮਣਕਿਆਂ ਦੀ ਪਵਿੱਤਰ ਮਾਲਾ ਸੰਪੂਰਨ ਹੋਈ।",
        acceptCelebration = "ਸਾਧਨਾ ਜਾਰੀ ਰੱਖੋ",
        settingsTitle = "ਸਾਧਨਾ ਸੰਕਲਪ ਅਤੇ ਸੈਟਿੰਗਾਂ",
        settingsSubtitle = "ਭਾਸ਼ਾ, ਰੋਜ਼ਾਨਾ ਨਿਯਮ, ਆਵਾਜ਼, ਕੰਬਣੀ",
        languageTitle = "ਭਾਸ਼ਾ ਚੁਣੋ (Select Language)",
        dailyGoalTitle = "ਰੋਜ਼ਾਨਾ ਸੰਕਲਪ ਟੀਚਾ (Daily Mala Goal)",
        customGoal = "ਕਸਟਮ ਟੀਚਾ",
        customMantra = "ਆਪਣਾ ਮੰਤਰ ਲਿਖੋ",
        soundFeedback = "ਜਪ ਆਵਾਜ਼ ਸੰਕੇਤ",
        vibrationFeedback = "ਕੰਬਣੀ ਤੀਬਰਤਾ",
        touchHardware = "ਟੱਚ ਅਤੇ ਹਾਰਡਵੇਅਰ",
        tapAnywhere = "ਪੂਰੀ ਸਕ੍ਰੀਨ 'ਤੇ ਛੋਹ",
        tapAnywhereDesc = "ਅੱਖਾਂ ਬੰਦ ਕਰਕੇ ਧਿਆਨ ਵਿੱਚ ਜਪਣ ਲਈ",
        volumeKeys = "ਵਾਲੀਅਮ ਬਟਨ ਨਾਲ ਜਪ",
        volumeKeysDesc = "ਸਕ੍ਰੀਨ ਦੇਖੇ ਬਿਨਾਂ ਵਾਲੀਅਮ ਬਟਨ ਦਬਾ ਕੇ ਜਪੋ",
        themeTitle = "ਥੀਮ (Theme Style)",
        themeGlass = "ਗਲਾਸ ਮੌਰਫਿਜ਼ਮ (Glassmorphism)",
        themeLight = "ਸਵੇਰ ਦੀ ਰੌਸ਼ਨੀ (Dawn Light)",
        themeDark = "ਰਾਤ ਦਾ ਧਿਆਨ (Temple Night)",
        themeAuto = "ਆਟੋ (Auto)",
        devotionalFooter = "ਸ਼੍ਰੀ ਰਾਧਾ ਰਾਣੀ ਜੀ ਦੇ ਚਰਨ ਕਮਲਾਂ ਵਿੱਚ ਸਮਰਪਿਤ",
        blessing = "ਜੈ ਜੈ ਸ਼੍ਰੀ ਰਾਧੇ! ਸ਼ੁਭ ਸਾਧਨਾ!",
        customMantraDialogTitle = "ਕਸਟਮ ਮੰਤਰ",
        enterMantra = "ਮੰਤਰ ਲਿਖੋ",
        submit = "ਸਵੀਕਾਰ ਕਰੋ",
        cancel = "ਰੱਦ ਕਰੋ",
        presetMantras = listOf(
            "ਸ਼੍ਰੀ ਰਾਧਾ",
            "ਰਾਧੇ ਰਾਧੇ",
            "ਹਰੇ ਕ੍ਰਿਸ਼ਨ ਹਰੇ ਕ੍ਰਿਸ਼ਨ ਕ੍ਰਿਸ਼ਨ ਕ੍ਰਿਸ਼ਨ ਹਰੇ ਹਰੇ \nਹਰੇ ਰਾਮ ਹਰੇ ਰਾਮ ਰਾਮ ਰਾਮ ਹਰੇ ਹਰੇ",
            "ਸ਼੍ਰੀ ਰਾਧਾ ਕ੍ਰਿਸ਼ਨ",
            "ਸ਼੍ਰੀ ਰਾਧਾ ਗੋਵਿੰਦ",
            "ੴ ਨਮੋ ਭਗਵਤੇ ਵਾਸੁਦੇਵਾਯ"
        ),
        devotionalQuotes = listOf(
            "« ਜੋ ਜਨ ਜਪੈ ਸਦਾ ਰਾਧਾ ਨਾਮ, ਤਿਸੁ ਬਿਘਨ ਨ ਲਾਗੈ ਕੋਈ। »",
            "« ਰਾਧਾ ਨਾਮ ਪਰਮ ਸੁਖਦਾਈ ਹੈ। »"
        )
    )

    private val assameseStrings = LanguageStrings(
        appName = "শ্ৰী ৰাধা নাম জপ",
        tabJap = "জপ (Jap)",
        tabSadhana = "সাধনা (Sadhana)",
        tabGoal = "সংকল্প (Goal)",
        selectMantra = "মন্ত্ৰ বাছনি কৰক",
        thisMantraMala = "এই মন্ত্ৰৰ মালা",
        bead = "মণি",
        totalToday = "আজি মুঠ",
        tapToChant = "স্পৰ্শ কৰক (Tap)",
        resetMala = "মালা ৰিচেট",
        undo = "পূৰ্বৱত (Undo)",
        sound = "ধ্বনি",
        dhyanMode = "শান্ত ধ্যান",
        todaySadhanaSummary = "আজিৰ সাধনা সাৰাংশ",
        totalChantedToday = "আজিৰ মুঠ জপ",
        malas = "মালা",
        beads = "মণি",
        sankalpaTarget = "সংকল্প লক্ষ্য",
        streak = "সাধনা ক্ৰম",
        days = "দিন",
        mantraWiseRecords = "মন্ত্ৰ অনুসৰি আজিৰ বিৱৰণ",
        lifetimeTotal = "আজীৱন মুঠ জপ",
        lifetimeMalas = "মুঠ মালা",
        lifetimeBeads = "মুঠ মণি",
        dhyanTitle = "শান্ত ধ্যান সাধনা",
        soundDrone = "তানপুৰা ওংকাৰ",
        tapAnywherePrompt = "চকু মুদি যিকোনো ঠাইত স্পৰ্শ কৰক",
        exitDhyan = "ধ্যান সমাপ্ত",
        exitTitle = "আপুনি ওলাই যাব বিচাৰে নেকি?",
        exitMessage = "আপোনাৰ আজিৰ সাধনা সুৰক্ষিতভাৱে সংৰক্ষিত হৈছে।",
        todayCompletedMalas = "আজি সম্পূৰ্ণ মালা",
        continueJap = "জপ অব্যাহত ৰাখক",
        confirmExit = "ওলাই যাওক",
        celebrationTitle = "মালা সম্পূৰ্ণ! জয় শ্ৰী ৰাধে!",
        celebrationDesc = "১০৮ মণিৰ পৱিত্ৰ মালা সম্পন্ন হ'ল।",
        acceptCelebration = "সাধনা অব্যাহত ৰাখক",
        settingsTitle = "সাধনা সংকল্প আৰু ছেটিংছ",
        settingsSubtitle = "ভাষা, দৈনিক নিয়ম, শব্দ, কম্পন আৰু থীম",
        languageTitle = "ভাষা বাছনি কৰক (Select Language)",
        dailyGoalTitle = "দৈনিক সংকল্প লক্ষ্য (Daily Mala Goal)",
        customGoal = "কাষ্টম লক্ষ্য",
        customMantra = "কাষ্টম মন্ত্ৰ লিখক",
        soundFeedback = "জপ ধ্বনি সংকেত",
        vibrationFeedback = "কম্পন তীব্ৰতা",
        touchHardware = "স্পৰ্শ আৰু হাৰ্ডৱেৰ",
        tapAnywhere = "সম্পূৰ্ণ স্ক্ৰীণত স্পৰ্শ",
        tapAnywhereDesc = "চকু মুদি ধ্যানস্থ জপৰ বাবে",
        volumeKeys = "ভলিউম বুটামেৰে জপ",
        volumeKeysDesc = "স্ক্ৰীণ নোচোৱাকৈ ভলিউম বুটাম টিপি জপ কৰক",
        themeTitle = "থীম (Theme Style)",
        themeGlass = "গ্লাচ মৰ্ফিজম (Glassmorphism)",
        themeLight = "উষাকাল (Dawn Light)",
        themeDark = "ৰাত্ৰি ধ্যান (Temple Night)",
        themeAuto = "স্বয়ংক্ৰিয় (Auto)",
        devotionalFooter = "শ্ৰী ৰাধাৰাণীৰ শ্ৰীচৰণ কমলত সমৰ্পিত",
        blessing = "জয় জয় শ্ৰী ৰাধে! শুভ সাধনা!",
        customMantraDialogTitle = "কাষ্টম মন্ত্ৰ",
        enterMantra = "মন্ত্ৰ লিখক",
        submit = "গ্ৰহণ কৰক",
        cancel = "বাতিল",
        presetMantras = listOf(
            "শ্ৰী ৰাধা",
            "ৰাধে ৰাধে",
            "হৰে কৃষ্ণ হৰে কৃষ্ণ কৃষ্ণ কৃষ্ণ হৰে হৰে \nহৰে ৰাম হৰে ৰাম ৰাম ৰাম হৰে হৰে",
            "শ্ৰী ৰাধা কৃষ্ণ",
            "ৰাধা গোবিন্দ",
            "ওঁ নমো ভগৱতে বাসুদেৱায়"
        ),
        devotionalQuotes = listOf(
            "« যিজনে জপে সদা ৰাধা নাম, তেওঁৰ সফল হয় সৱ কাম। »",
            "« ৰাধা নাম পৰম সুখদায়ক। »"
        )
    )

    private val sanskritStrings = LanguageStrings(
        appName = "श्रीराधानामन जपः",
        tabJap = "जपः (Jap)",
        tabSadhana = "साधना (Sadhana)",
        tabGoal = "सङ्कल्पः (Goal)",
        selectMantra = "मन्त्रं चिनुत",
        thisMantraMala = "अस्य मन्त्रस्य माला",
        bead = "मणिः",
        totalToday = "अद्य समग्रम्",
        tapToChant = "स्पर्शं कुरुत (Tap)",
        resetMala = "माला पुनर्निधानम्",
        undo = "पूर्ववत् (Undo)",
        sound = "ध्वनिः",
        dhyanMode = "शान्तध्यानम्",
        todaySadhanaSummary = "अद्यतनसाधनासारः",
        totalChantedToday = "अद्यतनजपसङ्ख्या",
        malas = "मालाः",
        beads = "मणयः",
        sankalpaTarget = "सङ्कल्पलक्ष्यम्",
        streak = "साधनाक्रमः",
        days = "दिनानि",
        mantraWiseRecords = "मन्त्रानुसारं विवरणम्",
        lifetimeTotal = "आजीवनं समग्रजपः",
        lifetimeMalas = "आजीवनमालाः",
        lifetimeBeads = "आजीवनमणयः",
        dhyanTitle = "शान्तध्यानसाधना",
        soundDrone = "तानपुरा ओंकारः",
        tapAnywherePrompt = "नेत्रे निमील्य यत्र कुत्रापि स्पृशत",
        exitDhyan = "ध्यानं समाप्तम्",
        exitTitle = "किं भवान् निर्गन्तुम् इच्छति?",
        exitMessage = "भवतः अद्यतनसाधना सुरक्षिता वर्तते।",
        todayCompletedMalas = "अद्य पूर्णाः मालाः",
        continueJap = "जपं निरन्तरं कुरुत",
        confirmExit = "निर्गच्छतु",
        celebrationTitle = "माला पूर्णा! जयतु श्रीराधे!",
        celebrationDesc = "अष्टोत्तरशत (१०८) मणीनां माला संवृत्ता।",
        acceptCelebration = "साधनां वर्धयतु",
        settingsTitle = "साधनासङ्कल्पः विन्यासाश्च",
        settingsSubtitle = "भाषा, नियमाः, ध्वनिः, स्पन्दनं च",
        languageTitle = "भाषां चिनुत (Select Language)",
        dailyGoalTitle = "दैनिकसङ्कल्पलक्ष्यम् (Daily Mala Goal)",
        customGoal = "इष्टलक्ष्यम्",
        customMantra = "इष्टमन्त्रलेखनम्",
        soundFeedback = "जपध्वनिसंकेतः",
        vibrationFeedback = "स्पन्दनतीव्रता",
        touchHardware = "स्पर्शः तन्त्रप्रणाली च",
        tapAnywhere = "सर्वत्र स्पर्शः",
        tapAnywhereDesc = "नेत्रे पिधाय ध्यानाय",
        volumeKeys = "ध्वनिगुल्मिकया जपः",
        volumeKeysDesc = "दृष्टिं विना गुल्मिकया जपः",
        themeTitle = "वर्णशैली (Theme Style)",
        themeGlass = "काचस्फटिकम् (Glassmorphism)",
        themeLight = "उषःकालः (Dawn Light)",
        themeDark = "नक्तध्यानम् (Temple Night)",
        themeAuto = "स्वचालितम् (Auto)",
        devotionalFooter = "श्रीराधाराण्याः पादपद्मेषु समर्पितम्",
        blessing = "जयतु जयतु श्रीराधे! शुभा साधना!",
        customMantraDialogTitle = "इष्टमन्त्रः",
        enterMantra = "मन्त्रं लिखतु",
        submit = "स्वीकुरुत",
        cancel = "त्यजतु",
        presetMantras = listOf(
            "श्री राधा",
            "राधे राधे",
            "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
            "श्री राधा कृष्ण",
            "राधा गोविन्द",
            "ॐ नमो भगवते वासुदेवाय"
        ),
        devotionalQuotes = listOf(
            "« राधा नाम परं मन्त्रं, सर्वसिद्धिप्रदायकम्। »",
            "« स्मरामि राधापदपङ्कजं सदा। »"
        )
    )

    private val maithiliStrings = LanguageStrings(
        appName = "श्री राधा नाम जप",
        tabJap = "जप (Jap)",
        tabSadhana = "साधना (Sadhana)",
        tabGoal = "संकल्प (Goal)",
        selectMantra = "मंत्र चुनू",
        thisMantraMala = "एहि मंत्रक माला",
        bead = "दाना",
        totalToday = "आइ कुल",
        tapToChant = "स्पर्श करू (Tap)",
        resetMala = "माला रीसेट",
        undo = "पछाति (Undo)",
        sound = "ध्वनि",
        dhyanMode = "शांत ध्यान",
        todaySadhanaSummary = "आइक साधना सारांश",
        totalChantedToday = "आइक कुल जप",
        malas = "माला",
        beads = "दाना",
        sankalpaTarget = "संकल्प लक्ष्य",
        streak = "साधना क्रम",
        days = "दिन",
        mantraWiseRecords = "मंत्र अनुसार आइक विवरण",
        lifetimeTotal = "आजीवन कुल जप",
        lifetimeMalas = "आजीवन माला",
        lifetimeBeads = "आजीवन दाना",
        dhyanTitle = "शांत ध्यान साधना",
        soundDrone = "तानपूरा ओंकार",
        tapAnywherePrompt = "आँखि मूंदि कतहू स्पर्श करू",
        exitDhyan = "ध्यान समाप्त",
        exitTitle = "की अपने बाहर निकलऽ चाहैत छी?",
        exitMessage = "अहाँक आइक साधना सुरक्षित रूप सँ सहेजल अछि।",
        todayCompletedMalas = "आइ पूर्ण माला",
        continueJap = "जप जारी राखू",
        confirmExit = "बाहर निकलू",
        celebrationTitle = "माला पूर्ण! जय श्री राधे!",
        celebrationDesc = "१०८ दानाक पावन माला संपन्न भेल।",
        acceptCelebration = "साधना जारी राखू",
        settingsTitle = "साधना संकल्प आ सेटिंग्स",
        settingsSubtitle = "भाषा, नियम, ध्वनि, स्पंदन आ थीम",
        languageTitle = "भाषा चुनू (Select Language)",
        dailyGoalTitle = "दैनिक संकल्प लक्ष्य (Daily Mala Goal)",
        customGoal = "कस्टम लक्ष्य",
        customMantra = "कस्टम मंत्र लिखू",
        soundFeedback = "जप ध्वनि संकेत",
        vibrationFeedback = "कंपन तीव्रता",
        touchHardware = "स्पर्श आ हार्डवेयर",
        tapAnywhere = "पूरा स्क्रीन पर स्पर्श",
        tapAnywhereDesc = "आँखि मूंदि क ध्यानस्थ जप लेल",
        volumeKeys = "वॉल्यूम बटन सँ जप",
        volumeKeysDesc = "स्क्रीन देखे बिना वॉल्यूम बटन सँ जप करू",
        themeTitle = "थीम (Theme Style)",
        themeGlass = "ग्लासमॉर्फिज्म (Glassmorphism)",
        themeLight = "उषाकाल (Dawn Light)",
        themeDark = "रात्रिकालीन ध्यान (Temple Night)",
        themeAuto = "सिस्टम (Auto)",
        devotionalFooter = "श्री राधा रानीक चरण कमलमे समर्पित",
        blessing = "जय जय श्री राधे! शुभ साधना!",
        customMantraDialogTitle = "कस्टम मंत्र",
        enterMantra = "मंत्र लिखू",
        submit = "स्वीकारू",
        cancel = "रद्द करू",
        presetMantras = listOf(
            "श्री राधा",
            "राधे राधे",
            "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
            "श्री राधा कृष्ण",
            "राधा गोविन्द",
            "ॐ नमो भगवते वासुदेवाय"
        ),
        devotionalQuotes = listOf(
            "« जे जन जपथि सदा राधा नाम, तिनका सिद्ध होइ सब काम। »",
            "« राधा नाम परम सुखदाई। »"
        )
    )

    private val nepaliStrings = LanguageStrings(
        appName = "श्री राधा नाम जप",
        tabJap = "जप (Jap)",
        tabSadhana = "साधना (Sadhana)",
        tabGoal = "संकल्प (Goal)",
        selectMantra = "मन्त्र छान्नुहोस्",
        thisMantraMala = "यो मन्त्रको माला",
        bead = "गेडा (मनका)",
        totalToday = "आज जम्मा",
        tapToChant = "छुनुहोस् (Tap)",
        resetMala = "माला रिसेट",
        undo = "पहिलेको (Undo)",
        sound = "ध्वनि",
        dhyanMode = "शान्त ध्यान",
        todaySadhanaSummary = "आजको साधना सारांश",
        totalChantedToday = "आजको जम्मा जप",
        malas = "माला",
        beads = "गेडा",
        sankalpaTarget = "संकल्प लक्ष्य",
        streak = "साधना क्रम",
        days = "दिन",
        mantraWiseRecords = "मन्त्र अनुसार आजको विवरण",
        lifetimeTotal = "जीवनभरको जम्मा जप",
        lifetimeMalas = "जम्मा माला",
        lifetimeBeads = "जम्मा गेडा",
        dhyanTitle = "शान्त ध्यान साधना",
        soundDrone = "तानपुरा ओंकार",
        tapAnywherePrompt = "आँखा चिम्लेर जहाँसुकै छुनुहोस्",
        exitDhyan = "ध्यान समाप्त",
        exitTitle = "के तपाईं बाहिर निस्कन चाहनुहुन्छ?",
        exitMessage = "तपाईंको आजको साधना सुरक्षित रूपमा सञ्चित छ।",
        todayCompletedMalas = "आज पूरा भएका माला",
        continueJap = "जप जारी राख्नुहोस्",
        confirmExit = "बाहिर निस्कनुहोस्",
        celebrationTitle = "माला पूर्ण! जय श्री राधे!",
        celebrationDesc = "१०८ गेडाको पवित्र माला पूरा भयो।",
        acceptCelebration = "साधना जारी राख्नुहोस्",
        settingsTitle = "साधना संकल्प र सेटिङहरू",
        settingsSubtitle = "भाषा, दैनिक नियम, ध्वनि, कम्पन र थिम",
        languageTitle = "भाषा छान्नुहोस् (Select Language)",
        dailyGoalTitle = "दैनिक संकल्प लक्ष्य (Daily Mala Goal)",
        customGoal = "आफ्नो लक्ष्य",
        customMantra = "आफ्नो मन्त्र लेख्नुहोस्",
        soundFeedback = "जप ध्वनि सङ्केत",
        vibrationFeedback = "कम्पन तीव्रता",
        touchHardware = "स्पर्श र हार्डवेयर",
        tapAnywhere = "पूरै स्क्रिनमा स्पर्श",
        tapAnywhereDesc = "आँखा चिम्लेर ध्यानमा जप गर्न",
        volumeKeys = "भोल्युम बटनबाट जप",
        volumeKeysDesc = "स्क्रिन नहेरी भोल्युम बटन थिचेर जप्नुहोस्",
        themeTitle = "थिम (Theme Style)",
        themeGlass = "ग्लासमर्फिजम (Glassmorphism)",
        themeLight = "प्रभात किरण (Dawn Light)",
        themeDark = "रात्रि ध्यान (Temple Night)",
        themeAuto = "अटो (Auto)",
        devotionalFooter = "श्री राधा रानीको पाउमा समर्पित",
        blessing = "जय जय श्री राधे! शुभ साधना!",
        customMantraDialogTitle = "आफ्नो मन्त्र",
        enterMantra = "मन्त्र लेख्नुहोस्",
        submit = "स्वीकार",
        cancel = "रद्द",
        presetMantras = listOf(
            "श्री राधा",
            "राधे राधे",
            "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
            "श्री राधा कृष्ण",
            "राधा गोविन्द",
            "ॐ नमो भगवते वासुदेवाय"
        ),
        devotionalQuotes = listOf(
            "« जो जन जप्छन् राधा नाम, उनको पूरा हुन्छ काम। »",
            "« राधा नाम परम सुखदायी। »"
        )
    )

    private val konkaniStrings = LanguageStrings(
        appName = "श्री राधा नाम जप",
        tabJap = "जप (Jap)",
        tabSadhana = "साधना (Sadhana)",
        tabGoal = "संकल्प (Goal)",
        selectMantra = "मंत्र वेंचून काढा",
        thisMantraMala = "ह्या मंत्राची माळ",
        bead = "मणी",
        totalToday = "आयज पुराय",
        tapToChant = "स्पर्श करात (Tap)",
        resetMala = "माळ रीसेट",
        undo = "फाटीं घेयात (Undo)",
        sound = "आवाज",
        dhyanMode = "शांत ध्यान",
        todaySadhanaSummary = "आयचो साधना सारांश",
        totalChantedToday = "आयचो पुराय जप",
        malas = "माळो",
        beads = "मणी",
        sankalpaTarget = "संकल्प ध्येय",
        streak = "साधना क्रम",
        days = "दीस",
        mantraWiseRecords = "मंत्र प्रमाणें आयचो तपशील",
        lifetimeTotal = "आयुष्यभराचो पुराय जप",
        lifetimeMalas = "पुराय माळो",
        lifetimeBeads = "पुराय मणी",
        dhyanTitle = "शांत ध्यान साधना",
        soundDrone = "तानपुरा ओंकार",
        tapAnywherePrompt = "दोळे धांपून खंयच स्पर्श करात",
        exitDhyan = "ध्यान सोंपलें",
        exitTitle = "तुमी भायर सरूंक सोदतात?",
        exitMessage = "तुमची आयची साधना सुरक्षीत दवरल्या.",
        todayCompletedMalas = "आयज पुराय जाल्ल्यो माळो",
        continueJap = "जप चालू दवरात",
        confirmExit = "भायर सरात",
        celebrationTitle = "माळ पुराय! जय श्री राधे!",
        celebrationDesc = "१०८ मण्यांची पवित्र माळ संपन्न जाली.",
        acceptCelebration = "साधना चालू दवरात",
        settingsTitle = "साधना संकल्प आनी मांडणी",
        settingsSubtitle = "भास, नेम, आवाज, कंपन आनी थीम",
        languageTitle = "भास वेंचून काढा (Select Language)",
        dailyGoalTitle = "दैनिक संकल्प ध्येय (Daily Mala Goal)",
        customGoal = "खाजगी ध्येय",
        customMantra = "खाजगी मंत्र घालात",
        soundFeedback = "जप आवाज संकेत",
        vibrationFeedback = "कंपन तीव्रता",
        touchHardware = "स्पर्श आनी हार्डवेअर",
        tapAnywhere = "सगळ्या पडद्यार स्पर्श",
        tapAnywhereDesc = "दोळे धांपून ध्यानांत जप करपाक",
        volumeKeys = "व्हॉल्यूम बटनान जप",
        volumeKeysDesc = "पडदो पळयनासतना व्हॉल्यूम बटनान जप करात",
        themeTitle = "थीम (Theme Style)",
        themeGlass = "ग्लासमॉर्फिजम (Glassmorphism)",
        themeLight = "उजाड (Dawn Light)",
        themeDark = "रातीचें ध्यान (Temple Night)",
        themeAuto = "आपसूक (Auto)",
        devotionalFooter = "श्री राधा राणीच्या पांयां कडेन समर्पित",
        blessing = "जय जय श्री राधे! बरी साधना!",
        customMantraDialogTitle = "खाजगी मंत्र",
        enterMantra = "मंत्र बरयात",
        submit = "मान्य",
        cancel = "रद्द",
        presetMantras = listOf(
            "श्री राधा",
            "राधे राधे",
            "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे \nहरे राम हरे राम राम राम हरे हरे",
            "श्री राधा कृष्ण",
            "राधा गोविंद",
            "ॐ नमो भगवते वासुदेवाय"
        ),
        devotionalQuotes = listOf(
            "« जो जपता सदा राधा नाम, ताका पावता भगवान। »",
            "« राधा नाम परम सुखदायी। »"
        )
    )

    private val englishStrings = LanguageStrings(
        appName = "Radha Jap Counter",
        tabJap = "Jap",
        tabSadhana = "Sadhana",
        tabGoal = "Goal",
        selectMantra = "Select Mantra",
        thisMantraMala = "Current Mala",
        bead = "Bead",
        totalToday = "Today Total",
        tapToChant = "Tap to Chant",
        resetMala = "Reset Mala",
        undo = "Undo",
        sound = "Sound",
        dhyanMode = "Deep Dhyan",
        todaySadhanaSummary = "Today's Sadhana Summary",
        totalChantedToday = "Total Chanted Today",
        malas = "Malas",
        beads = "Beads",
        sankalpaTarget = "Daily Goal",
        streak = "Streak",
        days = "Days",
        mantraWiseRecords = "Mantra-Wise Records",
        lifetimeTotal = "Lifetime Total",
        lifetimeMalas = "Lifetime Malas",
        lifetimeBeads = "Lifetime Beads",
        dhyanTitle = "Peaceful Dhyan Meditation",
        soundDrone = "Tanpura Om Drone",
        tapAnywherePrompt = "Close eyes & tap anywhere on screen",
        exitDhyan = "End Dhyan",
        exitTitle = "Do you want to exit?",
        exitMessage = "Your sadhana progress is safely saved.",
        todayCompletedMalas = "Today Completed Malas",
        continueJap = "Continue Chanting",
        confirmExit = "Exit App",
        celebrationTitle = "Mala Completed! Jai Sri Radhe!",
        celebrationDesc = "Sacred 108 beads mala completed with devotion.",
        acceptCelebration = "Continue Sadhana",
        settingsTitle = "Sadhana Goals & Settings",
        settingsSubtitle = "Language, daily targets, sounds, haptics & theme",
        languageTitle = "Select Language (16 Indian Languages)",
        dailyGoalTitle = "Daily Mala Goal (Sankalpa)",
        customGoal = "Custom Goal",
        customMantra = "Custom Mantra",
        soundFeedback = "Chant Audio Chime",
        vibrationFeedback = "Haptic Vibration",
        touchHardware = "Touch & Hardware",
        tapAnywhere = "Full Screen Tap",
        tapAnywhereDesc = "Meditate with eyes closed and tap anywhere",
        volumeKeys = "Volume Keys Counter",
        volumeKeysDesc = "Chant using physical volume keys without looking",
        themeTitle = "Theme Style",
        themeGlass = "Glassmorphism",
        themeLight = "Dawn Light",
        themeDark = "Temple Night",
        themeAuto = "System Auto",
        devotionalFooter = "Dedicated at the Lotus Feet of Sri Radha Rani",
        blessing = "Jai Jai Sri Radhe! Blessed Chanting!",
        customMantraDialogTitle = "Custom Mantra",
        enterMantra = "Enter Mantra Text",
        submit = "Save",
        cancel = "Cancel",
        presetMantras = listOf(
            "Sri Radha",
            "Radhe Radhe",
            "Hare Krishna Hare Krishna Krishna Krishna Hare Hare \nHare Rama Hare Rama Rama Rama Hare Hare",
            "Sri Radha Krishna",
            "Radha Govinda",
            "Om Namo Bhagavate Vasudevaya"
        ),
        devotionalQuotes = listOf(
            "« Chanting Sri Radha's holy name brings eternal peace and bliss. »",
            "« The nectar of Sri Radha's name purifies all minds. »",
            "« Every bead chanted with love draws you closer to the divine. »"
        )
    )
}
