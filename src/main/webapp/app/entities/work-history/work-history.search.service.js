(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('WorkHistorySearch', WorkHistorySearch);

  WorkHistorySearch.$inject = ['$resource'];

  function WorkHistorySearch($resource) {
    var resourceUrl = 'api/_search/work-histories/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
