package org.example.module.numberconversion.route;

/**
 * This enum represents the routes that this library exposes.
 * Each enum value represents a route and has an associated operation name.
 * When invoking the routes, the response will have on the header
 * the operation name associated with them.
 *
 * To use these routes, they should be added to the Camel context as follows:
 * <pre>
 * {@code
 * camelContext.addRoutes(new NumberToWordsConversionRoute());
 * camelContext.addRoutes(new NumberToDollarsConversionRoute());
 * }
 * </pre>
 */
public enum NumberConversionRoutes {
    /**
     * This route converts a number to words.
     * The operation name associated with this route is "NumberToWords".
     */
    NUMBER_CONVERSION_TO_WORDS_ROUTE("direct:numberConversionToWords", "NumberToWords", "number-to-words"),
    /**
     * This route converts a number to dollars.
     * The operation name associated with this route is "NumberToDollars".
     */
    NUMBER_CONVERSION_TO_DOLLARS_ROUTE("direct:numberConversionToDollars", "NumberToDollars", "number-to-dollars");

    private final String route;
    private final String operationName;
    private final String routeId;

    /**
     * Constructs a new NumberConversionEnum with the specified route and operation name.
     *
     * @param route the route associated with the enum value
     * @param operationName the operation name associated with the enum value
     * @param routeId the route id associated with the enum value
     */
    NumberConversionRoutes(String route, String operationName, String routeId) {
        this.route = route;
        this.operationName = operationName;
        this.routeId = routeId;
    }

    /**
     * Returns the route associated with the enum value.
     *
     * @return the route associated with the enum value
     */
    public String getRoute() {
        return this.route;
    }

    /**
     * Returns the operation name associated with the enum value.
     *
     * @return the operation name associated with the enum value
     */
    public String getOperationName() {
        return this.operationName;
    }

    /**
     * Returns the route id associated with the enum value.
     *
     * @return the route id associated with the enum value
     */
    public String getRouteId() {
        return this.routeId;
    }

}
