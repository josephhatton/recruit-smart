(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('HotListSearch', HotListSearch);

  HotListSearch.$inject = ['$resource'];

  function HotListSearch($resource) {
    var resourceUrl = 'api/_search/hot-lists/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
