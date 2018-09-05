document.addEventListener('DOMContentLoaded', ()=> {
	new Persons();
});
class Persons {
	constructor() {
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
			console.log('create!');
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
}
