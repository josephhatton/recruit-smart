(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('WorkStatusSearch', WorkStatusSearch);

  WorkStatusSearch.$inject = ['$resource'];

  function WorkStatusSearch($resource) {
    var resourceUrl = 'api/_search/work-statuses/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
