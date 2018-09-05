class DateTimeUtils {
	static toYMDHMS(dt, withSeconds = true) {
		if (isNaN(dt.getTime())) {
			return '';
		}
		let result;
		let yyyy = dt.getFullYear();
		let mm = ('0' + (dt.getMonth() + 1)).slice(-2);
		let dd = ('0' + dt.getDate()).slice(-2);
		let h = ('0' + dt.getHours()).slice(-2);
		let m = ('0' + dt.getMinutes()).slice(-2);

		result = yyyy + '/' + mm + '/' + dd + ' ' + h + ':' + m;
		if (withSeconds) {
			let s = ('0' + dt.getSeconds()).slice(-2);

			result += ':' + s;
		}
		return result;
	}

	static toYMDHM(dt) {
		return DateTimeUtils.toYMDHMS(dt, false);
	}

	static makeISO8601Offset(dt = new Date()) {
		if (isNaN(dt.getTime())) {
			return '';
		}
		let result;
		let yyyy = dt.getFullYear();
		let mm = ('0' + (dt.getMonth() + 1)).slice(-2);
		let dd = ('0' + dt.getDate()).slice(-2);
		let h = ('0' + dt.getHours()).slice(-2);
		let m = ('0' + dt.getMinutes()).slice(-2);
		let offset = dt.getTimezoneOffset() / 60;
		let offsetText = ('0' + Math.abs(offset)).slice(-2) + ':00';
		let sign = offset < 0 ? '+' : '-';

		return yyyy + '-' + mm + '-' + dd
				+ 'T' + h + ':' + m + ':00'
				+ sign + offsetText;
	}

	static makeISO8601(ymd, hms = null, defaultTime = '00:00') {

		if (ymd.length == 0) {
			return null;
		}
		let str = ymd;

		if (hms != null && 0 < hms.length) {
			str += ' ' + hms;
		} else {
			str += ' ' + defaultTime;
		}
		return moment(str, 'YYYY-MM-DD HH:mm:ss').toISOString();
	}

	static addDays(str, days) {
		let mom = moment(str, 'YYYY-MM-DD');

		mom.add(days, 'days');
		return mom.toISOString();
	}

	static sub(str, durationType) {
		let mom = moment(str, 'YYYY-MM-DD');

		if (durationType == 'ThreeMonths') {
			mom.add(-3, 'months');
		} else if (durationType == 'SixMonths') {
			mom.add(-6, 'months');
		} else if (durationType == 'OneYear') {
			mom.add(-1, 'year');
		} else if (durationType == 'TwoYears') {
			mom.add(-2, 'years');
		} else if (durationType == 'ThreeYears') {
			mom.add(-3, 'years');
		}
		return mom.toISOString();
	}
}
