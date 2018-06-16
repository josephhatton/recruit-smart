(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('JobOrderSearch', JobOrderSearch);

  JobOrderSearch.$inject = ['$resource'];

  function JobOrderSearch($resource) {
    var resourceUrl = 'api/_search/job-orders/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
