(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('HotListBucketSearch', HotListBucketSearch);

  HotListBucketSearch.$inject = ['$resource'];

  function HotListBucketSearch($resource) {
    var resourceUrl = 'api/_search/hot-list-buckets/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
