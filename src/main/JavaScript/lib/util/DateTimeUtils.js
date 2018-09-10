class DateTimeUtils {
	static format(fmt, dt = new Date()) {
		if (!dt || isNaN(dt.getTime())) {
			return '';
		}
		// 'yyyyMMdd-HHmmss'
		let result = fmt;

		result = result.replace(/yyyy/, dt.getFullYear());
		result = result.replace(/MM/, ('0' + (dt.getMonth() + 1)).slice(-2));
		result = result.replace(/dd/, ('0' + dt.getDate()).slice(-2));
		result = result.replace(/HH/, ('0' + dt.getHours()).slice(-2));
		result = result.replace(/mm/, ('0' + dt.getMinutes()).slice(-2));
		result = result.replace(/ss/, ('0' + dt.getSeconds()).slice(-2));
		result = result.replace(/yy/, dt.getFullYear() % 100);
		return result;
	}
}
