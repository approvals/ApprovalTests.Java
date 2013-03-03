package com.spun.util;


public enum Country {
  Albania, Algeria, AmericanSamoa("American Samoa"), Andorra, Angola, Anguilla, AntiguaAndBarbuda("Antigua & Barbuda"), Argentina, Armenia, Aruba, Australia, Austria, Azerbaijan, Azores, Bahamas, Bahrain, Bangladesh, Barbados, Belarus, Belgium, Belize, Benin, Bermuda, Bolivia, Bonaire, Bosnia, Botswana, Brazil, BritishVirginIsles(
      "British Virgin Isles"), Brunei, Bulgaria, BurkinaFaso("Burkina Faso"), Burundi, Cambodia, Cameroon, Canada, CanaryIslands("Canary Islands"), CapeVerde(
      "Cape Verde"), CaymanIslands("Cayman Islands"), CentralAfricanRepublic("Central African Republic"), Chad, ChannelIslands("Channel Islands"), Chile, Colombia, CookIslands(
      "Cook Islands"), CostaRica("Costa Rica"), CotedIvoire("Cote d' Ivoire"), Croatia, Cuba, Curacao, Cyprus, CzechRepublic("Czech Republic"), DemRepublicofCongo(
      "Dem. Republic of Congo"), Denmark, Djibouti, Dominica, DominicanRepublic("Dominican Republic"), Ecuador, Egypt, ElSalvador("El Salvador"), England, EquatorialGuinea(
      "Equatorial Guinea"), Eritrea, Estonia, Ethiopia, FaeroeIslands("Faeroe Islands"), Fiji, Finland, France, FrenchGuiana("French Guiana"), FrenchPolynesia(
      "French Polynesia"), Gabon, Gambia, Georgia, Germany, Ghana, Gibraltar, Greece, Greenland, Grenada, Guadeloupe, Guam, Guatemala, Guinea, GuineaBissau(
      "Guinea-Bissau"), Guyana, Haiti, Holland, Honduras, HongKong("Hong Kong"), Hungary, Iceland, India, Indonesia, Iran, Iraq, Israel, Italy, Jamaica, Japan, Jordan, Kazakhstan, Kenya, Kiribati, Kosrae, Kuwait, Kyrgyzstan, Laos, Latvia, Lebanon, Lesotho, Liberia, Libya, Liechtenstein, Lithuania, Luxembourg, Macau, Macedonia, Madagascar, Madeira, Malawi, Malaysia, Maldives, Mali, Malta, MarshallIslands(
      "Marshall Islands"), Martinique, Mauritania, Mauritius, Mexico, Micronesia, Moldova, Monaco, Mongolia, Montserrat, Morocco, Mozambique, Myanmar, NMarianaIslands(
      "N. Mariana Islands"), Namibia, Nepal, Netherlands, NetherlandsAntilles("Netherlands Antilles"), NewCaledonia("New Caledonia"), NewZealand(
      "New Zealand"), Nicaragua, Niger, Nigeria, NorfolkIsland("Norfolk Island"), NorthKorea("North Korea"), NorthernIreland("Northern Ireland"), Norway, Oman, Pakistan, Palau, Panama, PapuaNewGuinea(
      "Papua New Guinea"), Paraguay, PeoplesRepublicofChina("Peoples Republic of China"), Peru, Philippines, Poland, Ponape, Portugal, PuertoRico(
      "Puerto Rico"), Qatar, RepublicofCongo("Republic of Congo"), RepublicofIreland("Republic of Ireland"), RepublicofYemen("Republic of Yemen"), Reunion, Romania, Rota, Russia, Rwanda, Saba, Saipan, SanMarino(
      "San Marino"), SaudiArabia("Saudi Arabia"), Scotland, Senegal, Seychelles, SierraLeone("Sierra Leone"), Singapore, Slovakia, Slovenia, SolomonIslands(
      "Solomon Islands"), SouthAfrica("South Africa"), SouthKorea("South Korea"), Spain, SriLanka("Sri Lanka"), StVincentGrenadine(
      "St Vincent/Grenadine"), StBarthelemy("St. Barthelemy"), StChristopher("St. Christopher"), StCroix("St. Croix"), StEustatius("St. Eustatius"), StJohn(
      "St. John"), StKittsAndNevis("St. Kitts & Nevis"), StLucia("St. Lucia"), StMaarten("St. Maarten"), StMartin("St. Martin"), StThomas(
      "St. Thomas"), Sudan, Suriname, Swaziland, Sweden, Switzerland, Syria, Tahiti, Taiwan, Tajikistan, Tanzania, Thailand, Tinian, Togo, Tonga, Tortola, TrinidadAndTobago(
      "Trinidad & Tobago"), Truk, Tunisia, Turkey, Turkmenistan, TurksAndCaicosIsles("Turks & Caicos Isles"), Tuvalu, Uganda, Ukraine, UnionIsland(
      "Union Island"), UnitedArabEmirates("United Arab Emirates"), UnitedKingdom("United Kingdom"), UnitedStates("United States"), Uruguay, USVirginIslands(
      "US Virgin Islands"), Uzbekistan, Vanuatu, VaticanCityState("Vatican City State"), Venezuela, Vietnam, VirginGorda("Virgin Gorda"), WakeIsland(
      "Wake Island"), Wales, WallisAndFutunaIsle("Wallis & Futuna Isle"), WesternSamoa("Western Samoa"), Yap, Yugoslavia, Zambia, Zimbabwe;
  String altText = null;
  private Country()
  {
  }
  private Country(String altText)
  {
    this.altText = altText;
  }
  public String toString()
  {
    return altText == null ? super.toString() : altText;
  }
  private static String[] names = null;
  public synchronized static String[] getStringValues()
  {
    if (names == null)
    {
      names = (String[]) ObjectUtils.extractArray(Country.values(),"toString");
    }
    return names;
  }
}