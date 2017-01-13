To do:

- When no information is saved, delete row

- Descriptions

- Recommendations

- More user control

- re-roll ability scores, put own guidelines, etc

- make drag-and-drop not depend on UI choices

- inprogress characters with name = ""
	on selection screen, shows the finished character layout
	when click, acts like clicks index of finished character (ie 2nd "" character click -> 2 finished character details show)
	UPDATE: also happens with top in-progress, even with a name!="", but not with just one
	UPDATE: I think I figure out the problem, the id exists in finished, so thinks it's finished when it's not, need
			to have some other identifier for finished or not, like unique names or something

- update ability scores when back from selecting them




Bugs:

- Click select then an in-progress character
	When click finish, it returns to Select screen, but isn't refreshed

- Click select
	Creates empty bar at end of finished table
	When click empty bar, opens possibly first in created table

- On ability score selector
	can replace one option with another






Done:
- detail:
	shows name and ability scores for finished characters

- select:
	clicking on finished character launches detail fragment
	clicking on in-progress character launches create fragment (and edits entry instead of creating new one)

- create:
	multi page, navigate with swipes
	save into temporary database on edit (name, ability)
	save into permanent database on click create (name, ability)

- ability score selector:
	drag and drop interface for correctly generated stats to choose from










