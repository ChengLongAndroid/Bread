package com.funcy.g01.base.bo.fight;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class PhysicsContactHandler implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Object objA = contact.getFixtureA().getBody().getUserData();
		Object objB = contact.getFixtureB().getBody().getUserData();
		boolean isNeedCancel = false;
		if(objA instanceof FightUnit) {
			isNeedCancel |= ((FightUnit) objA).contactObj(objB, contact, true);
		} else if(objB instanceof FightUnit) {
			isNeedCancel |= ((FightUnit) objB).contactObj(objA, contact, false);
		}
//		if(isNeedCancel) {
//			contact.setEnabled(false);
//		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
