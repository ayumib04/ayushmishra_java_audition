package PreProcessData;

public class test {

	public static void main(String...arg){
		String line = "]]><data itemCount=\"104\" program=\"MaintainSQL\" programVersion=\"2.4\"><nutrientNode/><recipe createDate=\"05/13/2003\" description=\"Apple Brown Betty\" modifyDate=\"06/12/2008\" nutrientRefNum=\"79296705\" primaryKey=\"79296705\" recordType=\"insert\" useStaticIngrStmt=\"false\" userCode=\"3704\" version=\"2.2.0\"><labelOption buildInsigFootnote=\"true\" calcDVRaw=\"true\" hide449Footnote=\"false\" hideGramWeight=\"false\" hideInsigFootnote=\"false\" hideServings=\"false\" labelDVStandard=\"1\" labelElementType=\"0\" labelFootnoteOrientation=\"1.000000\" labelStyleFormat=\"0\" rrUSCalories=\"0\" rrUSCaloriesFat=\"0\" rrUSCarbs=\"0\" rrUSCholesterol=\"0\" rrUSProtein=\"0\" rrUSSugars=\"0\" rrUSVitMin=\"0\" showStandardFootnote=\"true\" useAbbrev=\"false\"><languageOption languageEnum=\"1\" servingSizeText=\"\" servingsContainerText=\"\"/></labelOption><labelPageLayout labelPageObjectHeight=\"0\" labelPageObjectLeft=\"0.500000\" labelPageObjectTop=\"0.500000\" labelPageObjectType=\"2\" labelPageObjectWidth=\"1.870000\"/><labelPageLayout labelPageObjectHeight=\"0\" labelPageObjectLeft=\"4.000000\" labelPageObjectTop=\"2.000000\" labelPageObjectType=\"3\" labelPageObjectWidth=\"0.860000\"/><labelPageLayout labelPageObjectHeight=\"0\" labelPageObjectLeft=\"3.000000\" labelPageObjectTop=\"5.000000\" labelPageObjectType=\"4\" labelPageObjectWidth=\"2.000000\"/><GroupData><Group groupName=\"COMMON\"/></GroupData><Yield cookedYield=\"100\" percentRefuse=\"0\" yieldKey=\"1\"><measure gramWeight=\"-1.000000\" measureKey=\"31\" quantity=\"6.000000\"/></Yield><RecipeItem ItemName=\"Dry Bread Crumbs\" itemDataType=\"1\" itemDisplayOrder=\"0\" itemEshaCode=\"42004\" itemKey=\"42004\" itemMeasureKey=\"3\" itemQuantity=\"2.000000\" itemQuantityText=\"\" itemYieldKey=\"1\"/><RecipeItem ItemName=\"Salted Butter\" itemDataType=\"1\" itemDisplayOrder=\"1\" itemEshaCode=\"8000\" itemKey=\"8000\" itemMeasureKey=\"3\" itemQuantity=\"0.250000\" itemQuantityText=\"1/4\" itemYieldKey=\"1\"/><RecipeItem ItemName=\"Microwaved Peeled Apple Slices\" itemDataType=\"1\" itemDisplayOrder=\"2\" itemEshaCode=\"3009\" itemKey=\"3009\" itemMeasureKey=\"3\" itemQuantity=\"4.000000\" itemQuantityText=\"\" itemYieldKey=\"1\"/><RecipeItem ItemName=\"White Granulated Sugar\" itemDataType=\"1\" itemDisplayOrder=\"3\" itemEshaCode=\"25006\" itemKey=\"25006\" itemMeasureKey=\"3\" itemQuantity=\"0.500000\" itemQuantityText=\"\" itemYieldKey=\"1\"/><RecipeItem ItemName=\"Ground Cinnamon\" itemDataType=\"1\" itemDisplayOrder=\"4\" itemEshaCode=\"26003\" itemKey=\"26003\" itemMeasureKey=\"1\" itemQuantity=\"0.500000\" itemQuantityText=\"1/2\" itemYieldKey=\"1\"/><RecipeItem ItemName=\"Ground Nutmeg\" itemDataType=\"1\" itemDisplayOrder=\"5\" itemEshaCode=\"26026\" itemKey=\"26026\" itemMeasureKey=\"1\" itemQuantity=\"0.250000\" itemQuantityText=\"1/4\" itemYieldKey=\"1\"/><RecipeItem ItemName=\"Municipal Tap Water\" itemDataType=\"1\" itemDisplayOrder=\"6\" itemEshaCode=\"20041\" itemKey=\"20041\" itemMeasureKey=\"3\" itemQuantity=\"0.500000\" itemQuantityText=\"1/2\" itemYieldKey=\"1\"/><memo><![CDATA[SOURCE: ESHA Research]]>Success!";
		String line2 = "]]>";
		System.out.println(line.contains("]]>"));
		String[] split = line2.split("]]>");
		System.out.println(split[0].toString());
		//System.out.println(split[1].toString());
		//System.out.println(split[2].toString());
	}
}
