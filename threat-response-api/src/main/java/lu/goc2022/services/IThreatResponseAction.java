package lu.goc2022.services;

import java.util.function.Function;

import lu.goc2022.orm.PhishingEvent;

/**
 * An action to perform in response of a threat.
 * May be sending a SMS, calling an HTTP REST API, etc.
 */
public interface IThreatResponseAction extends Function<PhishingEvent, ThreatResponseResult> {

}
