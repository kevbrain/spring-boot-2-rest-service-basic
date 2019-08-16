package com.openshift.compare;

import java.util.LinkedList;

public class ResourceCompare {
	
	public String compareValue(String strA,String strB) {
		if (strA==null) strA="";
		if (strB==null) strB="";
		Diff_match_patch dmp = new Diff_match_patch();
		LinkedList<Diff_match_patch.Diff> diff = dmp.diff_main(strA,strB);
		dmp.diff_cleanupSemantic(diff);
		return dmp.diff_prettyHtml(diff);
	}

}
