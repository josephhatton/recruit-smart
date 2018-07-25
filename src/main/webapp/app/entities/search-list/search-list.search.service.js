(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('SearchListSearch', SearchListSearch);

  SearchListSearch.$inject = ['$resource'];

  function SearchListSearch($resource) {
    var resourceUrl = 'api/_search/search-lists/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
