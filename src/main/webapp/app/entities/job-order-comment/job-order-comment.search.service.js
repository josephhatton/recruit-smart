(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('JobOrderCommentSearch', JobOrderCommentSearch);

  JobOrderCommentSearch.$inject = ['$resource'];

  function JobOrderCommentSearch($resource) {
    var resourceUrl = 'api/_search/job-order-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
