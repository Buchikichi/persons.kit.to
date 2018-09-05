class ControllerBase {
	constructor(name) {
		let base = document.querySelector('base').getAttribute('href');

		this.base = base + name;
	}

	postJSON(uri, data) {
		return AjaxUtils.postJSON(uri, data).then(data => {
			if (!data.result) {
				throw data;
			}
			return data;
		}).catch(e => {
			if (e.status == 403) {
				let base = document.querySelector('base').getAttribute('href');

				location.href = base;
				return null;
			}
			NoticeDialog.show(e.message);
			return null;
		});
	}

	/**
	 * 一覧取得.
	 */
	list(data = {}) {
		let body = JSON.stringify(data);

		return this.postJSON(this.base + '/list', body);
	}

	/**
	 * レコード取得.
	 */
	select(data = {}) {
		let body = JSON.stringify(data);

		return this.postJSON(this.base + '/select', body);
	}

	/**
	 * レコード保存.<br/>(レコードが既に存在すれば更新、無ければ追加)
	 */
	save(data = {}) {
		let body = JSON.stringify(data);

		return this.postJSON(this.base + '/save', body);
	}

	/**
	 * レコード削除
	 */
	delete(data = {}) {
		let body = JSON.stringify(data);

		return this.postJSON(this.base + '/delete', body);
	}

	/**
	 * ファイル作成.<br/>(ダウンロード)
	 */
	create(filename, data = {}) {
		let body = JSON.stringify(data);

		return AjaxUtils.fetch(this.base + '/create', body).then(response => {
			return response.blob();
		}).then(blob => {
console.log('done:');
console.log(blob);
			let downloadUrl  = (window.URL || window.webkitURL).createObjectURL(blob);
			let anchor = document.createElement('a');

			document.body.appendChild(anchor);
			anchor.href = downloadUrl;
			anchor.download = filename;
			anchor.click();
			(window.URL || window.webkitURL).revokeObjectURL(downloadUrl);
		});
	}
}
