package com.openshift.compare;

import com.openshift.resources.Secret;

public class SecretCompare  extends ResourceCompare {
	
	private Secret secretA;
	
	private Secret secretB;

	public SecretCompare(Secret secretA, Secret secretB) {
		super();
		this.secretA = secretA;
		this.secretB = secretB;
	}

	public Secret getSecretA() {
		return secretA;
	}

	public void setSecretA(Secret secretA) {
		this.secretA = secretA;
	}

	public Secret getSecretB() {
		return secretB;
	}

	public void setSecretB(Secret secretB) {
		this.secretB = secretB;
	}
	
	public void compare() {
		if (secretA!=null && secretB!=null && secretA.getData()!=null && secretB.getData()!=null) {
			for (String keydatat :secretA.getData().keySet()) {
				secretB.getData().put(keydatat, secretB.getData().get(keydatat).replace("eyIxNzIuMzAuMS4xOjUwMDAiOnsid", "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"));
				secretB.getData().put(keydatat, compareValue(secretB.getData().get(keydatat),secretA.getData().get(keydatat)));
			}
		}
	}

}
