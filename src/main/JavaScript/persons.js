document.addEventListener('DOMContentLoaded', ()=> {
	new Persons();
});
class Persons {
	constructor() {
		this.persons = new PersonsController();
		this.setupEvents();
console.log('Persons.');
	}

	setupEvents() {
		let disuseList = document.getElementById('disuseList');
		let busyList = document.getElementById('busyList');
		let createButton = document.querySelector('button.create');

		this.setupSortableList();
		$('#disuseList li, #busyList li').each((ix, li) => {
			let anchor = li.querySelectorAll('a')[1];

			$(li).dblclick(()=> {
				if (li.parentNode.id == 'busyList') {
					disuseList.appendChild(li);
				} else {
					busyList.appendChild(li);
				}
				this.refreshSortableList();
			});
			anchor.addEventListener('click', e => {
				if (li.parentNode.id == 'busyList') {
					$('#familyKana').panel( "open" );
				}
			});
		});
		createButton.addEventListener('click', ()=> {
			let data = this.createConditions();
			let now = DateTimeUtils.toYMDHMS(new Date());
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
		$('#disuseList').listview('refresh');
		sortableList.listview('refresh');
	}

	refreshSortableList() {
		this.setupSortableList();
	}

	createConditions() {
		let form = document.getElementById('personsForm');
		let numberOfPersons = form.querySelector('[name=numberOfPersons]').value;
		let data = {numberOfPersons: numberOfPersons};

		return data;
	}
}
