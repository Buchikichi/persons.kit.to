document.addEventListener('DOMContentLoaded', ()=> {
	new Persons();
});

class Chooser {
	constructor(parent, li) {
		this.parent = parent;
		this.li = li;
		this.gear = li.querySelectorAll('a')[1];
		this.span = li.querySelector('span');
		this.href = this.gear.getAttribute('href').substr(1);
		this.panel = document.getElementById(this.href);
	}

	initialize() {
		let anchor = this.li.querySelectorAll('a')[0];
		let name = anchor.firstChild.textContent;
		// <div class="ui-body ui-body-b ui-corner-all title">'UUID'設定</div>
		let first = this.panel.querySelector('form');
		let title = document.createElement('div');

		title.textContent = name;
		['title', 'ui-body', 'ui-body-b', 'ui-corner-all'].forEach(c => title.classList.add(c));
		this.panel.insertBefore(title, this.panel.firstChild);
		// separator
		let template = document.getElementById('template').querySelector('fieldset');
		let fieldset = template.cloneNode(true);
		let inputName = this.panel.id + '.separator';
		let defaultSeparator = this.gear.getAttribute('data-separator');
		let labels = fieldset.querySelectorAll('label');

		Array.from(labels, (label, ix) => {
			let id = name + ix;
			let input = label.nextSibling;

			label.setAttribute('for', id);
			input.id = id;
			input.setAttribute('name', inputName);
		});
		if (defaultSeparator != null) {
			$(fieldset).find('input').val(defaultSeparator);
		}
		first.appendChild(fieldset);
		$(fieldset).controlgroup();
		$(this.panel).panel({
			close: (e, ui) => {
				this.setupDisplay();
			}
		});
	}

	setupDisplay() {
		if (this.li.parentNode.id == 'disuseList') {
			this.gear.classList.add('ui-state-disabled');
			this.span.style.display = 'none';
			return;
		}
		let val = this.separator;
		let sep = this.parent.separator == '\x09' ? 'TAB' : this.parent.separator;

		this.gear.classList.remove('ui-state-disabled');
		this.span.style.display = val === '' ? 'none' : 'inline';
		this.span.textContent = val == 1 ? sep : ' ';
	}

	get separator() {
		return this.panel.querySelector('[name$=separator]:checked').value;
	}

	get data() {
		let val = this.separator;
		let depends = this.gear.getAttribute('data-depends');
		let data = {
			name: this.href,
			separator: val == 1 ? this.parent.separator : val,
		};
		if (depends) {
			data.depends = depends;
		}
		return data;
	}
}

/**
 * Persons.
 */
class Persons {
	constructor() {
		this.persons = new PersonsController();
		this.disuseList = document.getElementById('disuseList');
		this.busyList = document.getElementById('busyList');
		this.setupEvents();
console.log('Persons.');
	}

	setupEvents() {
		let createButton = document.querySelector('button.create');
		let separator = document.querySelector('[name=separator]');
		let listItems = document.querySelectorAll('#disuseList li, #busyList li');

		Array.from(listItems, li => {
			let chooser = new Chooser(this, li);

			li.addEventListener('dblclick', ()=> {
				if (li.parentNode.id == 'busyList') {
					this.disuseList.appendChild(li);
				} else {
					this.busyList.appendChild(li);
				}
				this.refreshSortableList();
			});
			chooser.initialize();
		});
		separator.addEventListener('change', ()=> {
			this.refreshSortableList();
		});
		createButton.addEventListener('click', ()=> {
			let data = this.createConditions();
			let now = DateTimeUtils.format('yyMMdd-HHmmss');
			let filename = 'persons' + now + '.csv.gz';

			console.log('create:' + filename);
			$.mobile.loading('show', {text:'Creating...', textVisible:true});
			this.persons.create(filename, data).then(()=>{
				$.mobile.loading('hide');
			});
		});
		this.setupSortableList();
		this.refreshSortableList();
	}

	setupSortableList() {
		let sortableList = $('.sortableList');

		sortableList.sortable({
			placeholder: 'ui-state-highlight',
			update: () => {this.refreshSortableList()},
	    });
		$(this.disuseList).listview('refresh');
		sortableList.listview('refresh');
	}

	refreshSortableList() {
		let listItems = document.querySelectorAll('#disuseList li, #busyList li')

		Array.from(listItems, li => {
			new Chooser(this, li).setupDisplay();
		});
		this.setupSortableList();
	}

	createConditions() {
		let prefectures = $('[name=prefectures]').val();
		let numberOfPersons = document.querySelector('[name=numberOfPersons]').value;
		let data = {choosers: [], prefectures: prefectures, numberOfPersons: numberOfPersons};
		let listItems = this.busyList.querySelectorAll('li');

		Array.from(listItems, li => {
			let chooser = new Chooser(this, li);

			data.choosers.push(chooser.data);
		});
		return data;
	}

	get separator() {
		return document.querySelector('[name=separator]').value;
	}
}
