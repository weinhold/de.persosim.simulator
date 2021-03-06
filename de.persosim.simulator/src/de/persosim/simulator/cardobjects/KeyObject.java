package de.persosim.simulator.cardobjects;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.persosim.simulator.jaxb.KeyPairAdapter;

/**
 * This object wraps key objects for storing them in the object store.
 * They can be retrieved from there using their key reference id or additionally associated OIDs.
 * 
 * @author slutters
 *
 */
@XmlRootElement
public class KeyObject extends AbstractCardObject {
	
	@XmlElement
	@XmlJavaTypeAdapter(KeyPairAdapter.class)
	protected KeyPair keyPair;
	
	@XmlElement(name="keyIdentifier")
	protected KeyIdentifier primaryIdentifier;
	
	@XmlElementWrapper(name="usage")
	@XmlAnyElement(lax=true)
	protected Collection<CardObjectIdentifier> furtherIdentifiers = new ArrayList<>();
	
	
	public KeyObject() {
	}
	
	public KeyObject(KeyPair keyPair, KeyIdentifier identifier) {
		this.primaryIdentifier = identifier;
		this.keyPair = keyPair;
	}
	
	@Override
	public Collection<CardObjectIdentifier> getAllIdentifiers() {
		Collection<CardObjectIdentifier> allIdentifiers = new ArrayList<CardObjectIdentifier>();
		allIdentifiers.add(primaryIdentifier);
		allIdentifiers.addAll(furtherIdentifiers);
		return allIdentifiers;
	}

	/**
	 * This method adds an additional {@link OidIdentifier} object identifying objects implementing this interface.
	 * Associating one or more {@link OidIdentifier} objects with a {@link KeyObject} is the preferred way to indicate that the key material provided by {@link KeyObject} can be used with a given OID.
	 * Querying the object store for a concrete OID represented by an {@link OidIdentifier} will return all {@link KeyObject} objects with keys that can be used with the respective OID.
	 * @param oidIdentifier additional {@link OidIdentifier} object identifying objects implementing this interface
	 */
	public void addOidIdentifier(OidIdentifier oidIdentifier) {
		furtherIdentifiers.add(oidIdentifier);
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public KeyIdentifier getPrimaryIdentifier() {
		return primaryIdentifier;
	}

}
