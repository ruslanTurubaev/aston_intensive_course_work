
Для реализации пагинации использовалась библиотека Paging 3.

Так как необходимо было реализовать отдельно загрузку данных из сети и из базы данных, то не использовался RemoteMediator.

Вместо этого вся загрузка данных осуществлялась с помощью PagingSource, PagingSource при загрузке в методе load определяет находится ли устройство в сети и реализует метод getItemsRemote, если есть сеть, или getItemsLocal, если сети недоступна.

Так как при наличии сети данные загружаются во ViewModel напрямую из сети, а не из базы данных, как в случае использования RemoteMediator, возможен сценарий когда при загрузке данных устройство подключено к сети и, соответсвенно, вызывается метод getDataRemote, но в момент загрузки сеть пропадает, в таком случае PagingSource вернет LoadResult.Error.

В соответствии с описанием библиотеки Paging 3 (https://developer.android.com/topic/libraries/architecture/paging/load-state) получить данные о состоянии загрузки (LoadState) может только адаптер. 

В связи с этим, в случае когда PagingSource возвращает LoadResult.Error, получить об этом информацию можно только на уровне адаптера.

В случае получения в LoadState LoadState.Error, адаптер вызывает у ViewModel метод onErrorOccur. В этом методе ViewModel анализирует характер ошибки, если ошибка наследуется от IOException, то, скорее всего, в момент загрузки пропала сеть и ViewModel повторно попытается загрузить данные, в случае если сеть будет все еще отсутствовать, то загрузка произойдёт из базы данных.

Данные в базе данных кэшируются на сутки (24 * 60 * 60 * 1000 = 86 400 000 mils), при первом пополнении базы данных устанавливается время первой загрузки, PagingSource в методе getDataRemote при каждом обогащении базы данных проверяет не вышел ли срок хранения, в случае если вышел, то вызывается метод refreshCache, который полностью очищает базу данных, а время первой загрузки данных устанавливается как null.

PagingSource в методе getDataRemote при каждом успешном получении данных из сети обогащает базу данных новыми данными и проверяет не установлено ли время первой загрузки как null, если да, то это первая загрузка в базу данных, PagingSource устанавливает текущее время как время первой загрузки в базу данных, срок хранения данных начинает считаться заново. 
