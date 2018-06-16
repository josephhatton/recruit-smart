(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('HiringContactSearch', HiringContactSearch);

  HiringContactSearch.$inject = ['$resource'];

  function HiringContactSearch($resource) {
    var resourceUrl = 'api/_search/hiring-contacts/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
