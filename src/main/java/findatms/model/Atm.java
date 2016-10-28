package findatms.model;

public class Atm {
	public class GeoLocation {
		private String lat;
		private String lng;

		public GeoLocation(String lat, String lng) {
			this.lat = lat;
			this.lng = lng;
		}
		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public String getLng() {
			return lng;
		}

		public void setLng(String lng) {
			this.lng = lng;
		}
	}

	public class Address {
		private String street;
		private String housenumber;
		private String postalcode;
		private String city;
		private GeoLocation geoLocation;
		
		public Address (String street, String housenumber, String postalcode, String city, GeoLocation geoLocation) {
			this.street = street;
			this.housenumber = housenumber;
			this.postalcode = postalcode;
			this.city = city;
			this.geoLocation = geoLocation;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getHousenumber() {
			return housenumber;
		}

		public void setHousenumber(String housenumber) {
			this.housenumber = housenumber;
		}

		public String getPostalcode() {
			return postalcode;
		}

		public void setPostalcode(String postalcode) {
			this.postalcode = postalcode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public GeoLocation getGeoLocation() {
			return geoLocation;
		}

		public void setGeoLocation(GeoLocation geoLocation) {
			this.geoLocation = geoLocation;
		}
	}

	private Address address;
	private String distance;
	private String type;
	
	//for testing
	public Atm(String type, String distance, String street, String houseNumber, String postalCode, String city, String lat, String lng) {
		this.address =  new Address(street, houseNumber, postalCode, city, new GeoLocation(lat, lng));
		this.distance = distance;
		this.type = type;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
