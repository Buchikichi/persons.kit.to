document.addEventListener('DOMContentLoaded', ()=> {
	new Persons();
});
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

		this.setupSortableList();
		$('#disuseList li, #busyList li').each((ix, li) => {
			let anchor = li.querySelectorAll('a')[0];
			let gear = li.querySelectorAll('a')[1];
			let selector = '#' + gear.getAttribute('href');

			console.log(selector);
			li.addEventListener('dblclick', ()=> {
				if (li.parentNode.id == 'busyList') {
					this.disuseList.appendChild(li);
				} else {
					this.busyList.appendChild(li);
				}
				this.refreshSortableList();
			});
		});
		createButton.addEventListener('click', ()=> {
			let data = this.createConditions();
			let now = DateTimeUtils.format('yyMMdd-HHmmss');
			let filename = 'persons' + now + '.csv.gz';

			console.log('create!!');
			this.persons.create(filename, data);
		});
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
		this.setupSortableList();
	}

	createConditions() {
		let form = document.getElementById('personsForm');
		let numberOfPersons = form.querySelector('[name=numberOfPersons]').value;
		let data = {choosers: [], numberOfPersons: numberOfPersons};

		this.busyList.querySelectorAll('li').forEach(li => {
			let anchor = li.querySelectorAll('a')[0];
			let gear = li.querySelectorAll('a')[1];
			let href = gear.getAttribute('href');
			let name = href.substring(1);
			let depends = gear.getAttribute('data-depends');
			let chooser = {name: name};

			if (depends) {
				chooser.depends = depends;
			}
			data.choosers.push(chooser);
		});
		return data;
	}
}
