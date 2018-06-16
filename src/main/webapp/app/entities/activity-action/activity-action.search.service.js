(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('ActivityActionSearch', ActivityActionSearch);

  ActivityActionSearch.$inject = ['$resource'];

  function ActivityActionSearch($resource) {
    var resourceUrl = 'api/_search/activity-actions/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
